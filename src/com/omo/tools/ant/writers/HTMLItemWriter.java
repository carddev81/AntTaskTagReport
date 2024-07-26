package com.omo.tools.ant.writers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

import com.omo.tools.ant.Configuration;
import com.omo.tools.ant.format.FormattedItem;

/**
 * This class handles writing an html report containing todo, fixme, and cleanme task tags.  
 * @author Richard Salas
 */
public class HTMLItemWriter implements ItemWriter {

    /**
     * This method will write output all task tags into an html format.
     * @param out - output stream that the writer uses
     * @param itemsList - item list containing all the formatted items / task tags
     */
    public void write(OutputStream out, List<FormattedItem> itemsList) {
        try {
            // lots and lots of html is going to go here =-(

            // html, meta, head, style, title tags here 
            out.write(Configuration.HTML_HEAD_ST.getBytes());
            out.write(Configuration.HTML_CSS.getBytes());
            out.write(Configuration.HTML_TITLE_OPEN.getBytes());
            out.write(Configuration.HTML_HEAD_END.getBytes());
            
            // start the body of the page here (application name and application info)
            out.write(Configuration.HTML_BODY_APPLICATION_HEADER_START.getBytes());
            out.write(Configuration.applicationName.getBytes());
            out.write(Configuration.HTML_BODY_APPLICATION_HEADER_END.getBytes());

            out.write(Configuration.HTML_BODY_APPLICATION_INFO_REPOSITORY.getBytes());
            out.write(Configuration.repository.getBytes());
            
            out.write(Configuration.HTML_BODY_APPLICATION_INFO_PULLED_FROM.getBytes());
            out.write(Configuration.branch.getBytes());
            
            out.write(Configuration.HTML_BODY_APPLICATION_INFO_BUILD_NAME.getBytes());
            out.write(Configuration.buildName.getBytes());
            
            out.write(Configuration.HTML_BODY_APPLICATION_INFO_END.getBytes());
            out.write(Configuration.HTML_PARA.getBytes());
            
            out.write(Configuration.HTML_HORIZONTAL_LINE.getBytes());
            // check to see if the itemsList is empty here and if not then we do some cool stuff like adding navigation links ect..
            if (!itemsList.isEmpty()) {
                Collections.sort(itemsList);
                /* summary table start */
                out.write(Configuration.HTML_SUMMARY_TABLE_FILES_1.getBytes());
                out.write(String.valueOf(itemsList.size()).getBytes());
                
                out.write(Configuration.HTML_SUMMARY_TABLE_TASKTAGS_2.getBytes());
                out.write(String.valueOf(FormattedItem.getTotalNumberOfTaskTags()).getBytes());
                out.write(Configuration.HTML_SUMMARY_TABLE_END_3.getBytes());
                /* summary table end */

                out.write(Configuration.HTML_HORIZONTAL_LINE.getBytes());
                
                /* files table start */
                out.write(Configuration.HTML_FILES_TABLE_1.getBytes());
                final StringBuffer filesString = new StringBuffer(); 
                for (FormattedItem fmtItem : itemsList) {
                    filesString.append(String.format(Configuration.HTML_FILES_TABLE_2_FMT, fmtItem.getItem().getFileName(), fmtItem.getItem().getOrigin(), fmtItem.getNumTaskTags()));
                }
                out.write(filesString.toString().getBytes());
                out.write(Configuration.HTML_FILES_TABLE_END.getBytes());
                /* files table end */
                
                out.write(Configuration.HTML_HORIZONTAL_LINE.getBytes());
            }
            
            // start writing the html table main results for display...
            for (FormattedItem item : itemsList) {
                try {
                    final String value = item.getValue();
                    out.write(value.getBytes());
                    out.write(Configuration.HTML_HORIZONTAL_LINE.getBytes()); // writing horizonalline
                    out.write(Configuration.HTML_BACK_TO_TOP_LINK.getBytes()); // writing the back to top link.
                } catch (final IOException e) {
                    throw new RuntimeException(String.format("IO error when writing item '%s'", item), e);
                }
            }
            out.write("</html>".getBytes());
        } catch (final IOException e) {
            throw new RuntimeException(String.format("IO error when writing item to HTML"), e);
        }
    }

}

