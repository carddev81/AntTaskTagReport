package com.omo.tools.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;

import com.omo.tools.ant.format.*;
import com.omo.tools.ant.parsers.GenericItemParser;
import com.omo.tools.ant.parsers.ItemParser;
import com.omo.tools.ant.writers.CSVItemWriter;
import com.omo.tools.ant.writers.HTMLItemWriter;
import com.omo.tools.ant.writers.ItemWriter;
import com.omo.tools.ant.writers.PlainTextItemWriter;
import com.omo.tools.ant.writers.XMLItemWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * A task that outputs TODOs to a file for all source files that contain them
 *
 * Modified Comment: Added code logic to this class for handling html reports and also added some additional logging.
 * @author Modified by Richard Salas
 */
public class TaskTagReportTask extends MatchingTask {

    private PrintStream outStream;
    private boolean shouldWriteToProperty;

    /**
     * The delimited string containing the origin and content of TODOs/FIXMEs.
     * e.g. "My.java: do something,Another.java: code some more"
     */
    private StringBuffer optBuffer;
    private static List<FormattedItem> itemsList;
    private ItemParser itemParser;
    private Map<Format, ItemFormatter> itemFormattersMap;
    private Map<Format, ItemWriter> itemWriterMap;

    public void init() {
        super.init();
    }

    public void execute() throws BuildException {
        super.execute();
        initRequiredFields();
        parseFiles();
        outputFindings();
    }

    /**
     * For all files in baseDir call processFile(f)
     */
    private void parseFiles() {
        final File baseDir = Configuration.baseDir;
        for (String fName : getDirectoryScanner(baseDir).getIncludedFiles()) {
            final File f = new File(format("%s/%s", baseDir.getAbsolutePath(), fName));
            processFile(f);
        }
    }

    /**
     * This method will output all the task tags into a formatted report.
     */
    private void outputFindings() {
        vlog(format("itemsList before output is %s", itemsList));

        if (shouldWriteToProperty) {
            getProject().setNewProperty(Configuration.property, optBuffer.toString());
        }

        ItemWriter itemWriter = itemWriterMap.get(Configuration.optFormat);
        log(format("Writing using itemWriter '%s'", itemWriter));

        if (itemWriter == null) {
            itemWriter = itemWriterMap.get(Format.DEFAULT);
            log(format("Warning: using DEFAULT output format because I couldn't find a writer for '%s'", Configuration.optFormat));
        }

        itemWriter.write(outStream, itemsList);
        outStream.flush();
        outStream.close();
    }

    /**
     * Scan the file and parse any and all TODO/FIXMEs into the <code>itemsList</code> field.
     *
     * @param f
     */
    private void processFile(File f) {
        try {
            log(format("Processing %s", f.getName()));
            final BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            int lineCounter = 0;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                lineCounter++;
                if (itemParser.containsItem(line)) {
                    final Item item = newItem(f, line);
                    item.setLineNumber(lineCounter);

                    // this only affects the lines for HTML format...
                    if (Configuration.regex != null && Format.HTML.equals(Configuration.optFormat)) {
                        final String replace = Configuration.replace == null ? "" : Configuration.replace;
                        vlog(format("Running pattern %s with replace '%s' against '%s'",
                                Configuration.regex, replace, item.getContent()));
                        item.setContent(item.getContent().replaceAll(Configuration.regex, replace));
                        vlog(format("Pattern produced '%s'", item.getContent()));
                    }
                    
                    FormattedItem fmtItem = formatItem(item);
                    log(format("Line number %s", lineCounter));

                    // if not html format then this applies...
                    if (Configuration.regex != null && !Format.HTML.equals(Configuration.optFormat)) {
                        final String replace = Configuration.replace == null ? "" : Configuration.replace;
                        vlog(format("Running pattern %s with replace '%s' against '%s'",
                                Configuration.regex, replace, fmtItem.getValue()));
                        fmtItem.setValue(fmtItem.getValue().replaceAll(Configuration.regex, replace));
                        vlog(format("Pattern produced '%s'", fmtItem.getValue()));
                    }

                    // checking for null here because if this is an html report being generated i'm beatifying it by
                    // combining the items together where I can no point in repeating the name every single time there is a todo.
                    if (fmtItem != null) {
                        itemsList.add(fmtItem);
                        vlog(format("Added item to itemsList: %s", fmtItem));
                    }

                    //TODO: write only to the list; remove this to the output phase
                    if (shouldWriteToProperty) {
                        // buffer the val until later
                        // Part of a two-stage refactor; output to file is done later; buffering for property is still done here. We'll merge the two concepts later.
                        bufferValueForProperty(item);
                        vlog("Item buffered");
                    }
//                    }
                }
            }

        } catch (final IOException e) {
            log(format("Error! I couldn't write a TODO to the output file: %s", e.getMessage()));
        }

    }

    /**
     * This method formats task tags.
     * @param item the item object to format
     * @return FormattedItem - the item that was formatted
     */
    private FormattedItem formatItem(Item item) {
        //FIXME: do we risk a null pointer if the optFormat isn't recognisable?
        log("formatting a task tag");
        return itemFormattersMap.get(Configuration.optFormat).format(Configuration.optFormat, item);
    }

    /**
     * This method will handle initializing an item for formatting later in the program.
     * 
     * @param f - the file that contains the task tag
     * @param line - the line of text that is going to be used in creating the item
     * @return Item - the newly built item
     */
    private Item newItem(File f, String line) {
        final Item item = itemParser.parse(line);
        // FIXME: this has been customized for doc build scripts...by salas 
        String absoluteFilePath = f.getAbsolutePath();
        absoluteFilePath = absoluteFilePath.replace(getProject().getProperty("basedir") + "\\src\\", ""); 
        item.setFileName(f.getName());
        item.setOrigin(absoluteFilePath);// this sets origin of the file 
        return item;
    }

    /**
     * This method will sett up the property for the itme to go in...
     * @param item - item containing the task tag information
     */
    private void bufferValueForProperty(Item item) {
        final String todoStr = item.toString();
        optBuffer.append(todoStr).append(Configuration.delim);
        vlog(format("Buffered %s to property %s with delim %s",
                todoStr, Configuration.property, Configuration.delim));
    }

    /**
     * Check if we should be writing to a property instead of an output file.
     *
     * @return true if the property field is set
     */
    private boolean shouldWriteToProperty() {
        return Configuration.property != null && !"".equals(Configuration.property);
    }

    /**
     * This method initializes all the attributes that are used in this task from within the (build.xml or ant script).
     * @throws BuildException exception thrown when trying to initialize the values of each field.
     */
    private void initRequiredFields() throws BuildException {
        if (Configuration.baseDir == null) {
            log("You didn't specify a directory to recurse, so I'll use the current directory by default. This might not be what you want...");
            Configuration.baseDir = new File(System.getProperty("user.dir"));
        }

        if (Configuration.outFile == null) {
            outStream = System.out;
            log("No output file specified; writing to stdout");
        } else {
            try {
                outStream = new PrintStream(Configuration.outFile);
                log(format("Using file '%s'", Configuration.outFile.getAbsoluteFile()));
            } catch (FileNotFoundException e) {
                log("Error! Couldn't find output file!");
                throw new BuildException(e);
            }
        }

        if (Configuration.regex != null) {
            if (Configuration.replace == null) {
                log("No replace provided for regex; replacing all occurrences with nothing");
            }
        }

        shouldWriteToProperty = shouldWriteToProperty();
        optBuffer = new StringBuffer();
        itemsList = new ArrayList<FormattedItem>();
        itemParser = new GenericItemParser();

        itemFormattersMap = new HashMap<Format, ItemFormatter>();
        itemWriterMap = new HashMap<Format, ItemWriter>();

        // TODO: we are leaving this like this: as this is more efficient why: set up a writer for each instance
        if (Format.DEFAULT.equals(Configuration.optFormat)){
            log("setting defualt formatter/plain text...");
            itemFormattersMap.put(Format.DEFAULT, new PlainTextItemFormatter());
            itemWriterMap.put(Format.DEFAULT, new PlainTextItemWriter());
        } else if (Format.CSV.equals(Configuration.optFormat)) {
            log("setting csv formatter");
            itemFormattersMap.put(Format.CSV, new CSVItemFormatter());
            itemWriterMap.put(Format.CSV, new CSVItemWriter());
        } else if (Format.XML.equals(Configuration.optFormat)) {
            log("setting xml formatter");
            itemFormattersMap.put(Format.XML, new XMLItemFormatter());
            itemWriterMap.put(Format.XML, new XMLItemWriter());
        } else if (Format.HTML.equals(Configuration.optFormat)) {
            log("rts000is setting html formatter");
            itemFormattersMap.put(Format.HTML, new HTMLItemFormatter());
            itemWriterMap.put(Format.HTML, new HTMLItemWriter());
        }

    }

    /**
     * Log <code>msg</code> if this.verbose is true
     */
    protected final void vlog(String msg) {
        if (Configuration.verbose) log(msg);
    }

    /**
     * Set the base directory for the source files
     * Required!
     */
    public void setDir(File dir) {
        Configuration.baseDir = dir;
    }

    /**
     * The file to write the TODOs to, or stdout otherwise
     */
    public void setOutFile(File file) {
        Configuration.outFile = file;
    }

    public void setVerbose(boolean verbose) {
        Configuration.verbose = verbose;
    }

    /**
     * What format to write the TODOs as. One of CSV, XML or the default (blank):
     * SourceFilename:TODO_CONTENT
     */
    public void setFormat(String format) {
        Configuration.optFormat = Format.fromString(format);
    }

    /**
     * Store the TODOs found in the property identified by <code>property</code>.
     *
     * @param property
     */
    public void setProperty(String property) {
        Configuration.property = property;
    }

    /**
     * Use the given String as the delimiter for each TODO item.
     * By default this is set to the system's line separator.
     */
    public void setDelim(String delim) {
        Configuration.delim = delim;
    }

    public void setPattern(String regex) {
        Configuration.regex = regex;
    }

    public void setReplace(String replace) {
        Configuration.replace = replace;
    }
    
    public void setApplicationName(String applicationName) {
        Configuration.applicationName = applicationName;
    }
    
    public void setRepository(String repository) {
        Configuration.repository = repository;
    }

    public void setBranchOrTagName(String branchOrTagName) {
        Configuration.branch = branchOrTagName;
    }

    public void setBuildName(String buildName) {
        Configuration.buildName = buildName;
    }
    
    /**
     * @return formatted item list
     */
    public static List<FormattedItem> getItemsList() {
        return itemsList;
    }

}

