package com.omo.tools.ant;

import java.io.File;

import com.omo.tools.ant.format.Format;


/**
 * A tidier way of representing the constants available to our todo task.
 * Moved here to ensure clarity of code in the TaskTagReportTask class (as it was growing!)
 * @author modified by Richard Salas
 **/
public final class Configuration {

    /* Constants */
    public static final String LINE_SEP = System.getProperty("line.separator");
    public static final byte[] LINE_SEP_BYTES = LINE_SEP.getBytes();
    
    /* HTML Constants */
    // head tag start
    public static final String HTML_HEAD_ST = "<html>" + LINE_SEP + "<head>" + LINE_SEP + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + LINE_SEP;
    
    // styles are added here
    public static final String HTML_CSS = "<style type=\"text/css\">" 
        + LINE_SEP + "\t .bannercell {border: 0px; padding: 0px;}"
        + LINE_SEP + "\t body {margin-left: 10; margin-right: 10; font:normal 80% arial,helvetica,sanserif; background-color:#FFFFFF; color:#000000;}"
        + LINE_SEP + "\t .a td {background: #efefef;}"
        + LINE_SEP + "\t th, td {text-align: left; vertical-align: top;}"
        + LINE_SEP + "\t th {font-weight:bold; background: #ccc; color: black;}"
        + LINE_SEP + "\t .sevenPct {width:7%;}"
        + LINE_SEP + "\t .eighty-sixPct {width:86%}"
        + LINE_SEP + "\t .fiftyPct {width:50%;}"
        + LINE_SEP + "\t .eightyPct {width:80%;}"
        + LINE_SEP + "\t .twentyPct {width:20%;}"
        + LINE_SEP + "\t table {width: 100%;}"
        + LINE_SEP + "\t table.log tr td, tr th {}"
        + LINE_SEP + "\t table, th, td {font-size:100%; border: none;}"
        + LINE_SEP + "\t h2 {font-weight:bold; font-size:140%; margin-bottom: 5;}"
        + LINE_SEP + "\t h3 {font-size:100%; font-weight:bold; background: #525D76; color: white; text-decoration: none; padding: 5px; margin-right: 2px; margin-left: 2px; margin-bottom: 0;}"
        + LINE_SEP + "</style>" + LINE_SEP;
    
    // title
    public static final String HTML_TITLE_OPEN = "<title>Task Tags Audit Report</title>" + LINE_SEP;
    public static final String HTML_HEAD_END = "</head>" + LINE_SEP;
    
    // application header...
    public static final String HTML_BODY_APPLICATION_HEADER_START = "<body>" + LINE_SEP + "\t<a name=\"top\"></a>" 
        + LINE_SEP + "\t <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
        + LINE_SEP + "\t\t<tr><td style=\"text-align:center\"><h2>";
    public static final String HTML_BODY_APPLICATION_HEADER_END = " Task Tags Audit Report</h2></td></tr>" + LINE_SEP;

    // application information...
    public static final String HTML_BODY_APPLICATION_INFO_REPOSITORY = "\t\t<tr><td style=\"text-align:left\"><b><u>Application Information</u></b><br><b>Repository: </b>";
    public static final String HTML_BODY_APPLICATION_INFO_PULLED_FROM = "<br><b>Pulled from: </b>";
    public static final String HTML_BODY_APPLICATION_INFO_BUILD_NAME = "<br><b>Build name: </b>";
    public static final String HTML_BODY_APPLICATION_INFO_END = "</td></tr>" + LINE_SEP + "\t</table>";
    
    public static final String HTML_PARA = LINE_SEP + "\t<p style=\"text-align:right\">Designed by <a href=\"http://jakarta.apache.org\">Ant</a> for viewing Task Tags found in source files.</p>";
    
    // horizontal rule line
    public static final String HTML_HORIZONTAL_LINE = LINE_SEP + "\t<hr size=\"1\" width=\"100%\" align=\"left\">" + LINE_SEP;
    
    // do if test before using these constants -- summary stuff then write a horizontal line 
    public static final String HTML_SUMMARY_TABLE_FILES_1 = "\t<h3>Summary</h3>" + LINE_SEP + "\t<table class=\"log\" border=\"0\" cellpadding=\"5\" cellspacing=\"2\">"
        + LINE_SEP + "\t\t<tr><th class=\"fiftyPct\">Files</th><th class=\"fiftyPct\">Task Tags</th></tr>"
        + LINE_SEP + "\t\t<tr class=\"a\"><td>";
    public static final String HTML_SUMMARY_TABLE_TASKTAGS_2 = "</td><td>";
    public static final String HTML_SUMMARY_TABLE_END_3 = "</td></tr>" + LINE_SEP + "\t</table>" + LINE_SEP;
    
    // do if test before using these constants -- files table 
    public static final String HTML_FILES_TABLE_1 = "\t<h3>Files</h3>" + LINE_SEP + "\t<table class=\"log\" border=\"0\" cellpadding=\"5\" cellspacing=\"2\" width=\"100%\">"
        + LINE_SEP + "\t\t<tr><th class=\"eightyPct\">Name</th><th class=\"twentyPct\">Task Tags</th></tr>";
    public static final String HTML_FILES_TABLE_2_FMT = LINE_SEP + "\t\t<tr class=\"a\"><td><a href=\"#r-%s\">%s</a></td><td>%s</td></tr>";
    public static final String HTML_FILES_TABLE_END = LINE_SEP + "\t</table>";
    
    // these strings contain formatter strings
    public static final String HTML_FORMAT_ITEM_1 = LINE_SEP + "\t<a name=\"r-%s\"></a>" + LINE_SEP + "<h3>File %s</h3>" + LINE_SEP + "\t<table class=\"log\" border=\"0\" cellpadding=\"5\" cellspacing=\"2\">" 
        + LINE_SEP + "\t\t<tr><th class=\"sevenPct\">Task Tag</th><th class=\"eighty-sixPct\">Description</th><th class=\"sevenPct\">Line</th></tr>"
        + LINE_SEP + "\t\t<tr class=\"a\"><td>%s</td><td>%s</td><td>%s</td></tr>"
        + LINE_SEP + "\t</table>";
    public static final String HTML_FORMAT_ITEM_2 = LINE_SEP + "\t\t<tr class=\"a\"><td>%s</td><td>%s</td><td>%s</td></tr>" 
        + LINE_SEP + "\t</table>";
    public static final String HTML_BACK_TO_TOP_LINK = LINE_SEP + "<a href=\"#top\">Back to top</a>";
    
    public static final String HTML_CLOSE = LINE_SEP + "</body>" + LINE_SEP + "</html>";
    
    /* End constants */
    
    /* Settings provided by attributes to the ant task */
    public static File outFile;
    public static File baseDir;
    public static boolean verbose;
    public static String delim = LINE_SEP;
    public static String property;
    public static Format optFormat = Format.DEFAULT;
    public static String regex;
    public static String replace;
    
    /* HTML settings only for html formats setting these all to empty string for making sure that we get no null pointers */
    public static String applicationName = "";
    public static String repository = "";
    public static String branch = "";
    public static String buildName = ""; 
    
    /* End settings */

    private Configuration() {}
}

