package com.omo.tools.ant.format;

import com.omo.tools.ant.Configuration;
import com.omo.tools.ant.Item;
import com.omo.tools.ant.TaskTagReportTask;

/**
 * This class will handle formatting / beautifying the task tags into an html report...for viewing the task 
 * tags found within an applications code.
 * 
 * @author Richard Salas JCCC May 05, 2014
 */
public class HTMLItemFormatter implements ItemFormatter {

    /**
     * Implemented method that will format the html code into a fine report for viewing purposes.
     * @return {@link FormattedItem}} - formatted item
     */
    public FormattedItem format(Format fmt, Item i) {
        boolean firstFmtItem = true; // this is flag used below
        final FormattedItem fi = new FormattedItem();
        fi.setItem(i);
        fi.setFormat(fmt);

        final Item item = fi.getItem();
        
        // looping through the list of items to see if we can do some grouping...
        for (FormattedItem fmtItem : TaskTagReportTask.getItemsList()) {
            if (fmtItem.getItem().getOrigin().equals(item.getOrigin())) {
                // here i am using the string format method for my convienence as this is a nice clean way to format a string
                fmtItem.appendToValue(String.format(Configuration.HTML_FORMAT_ITEM_2, item.getType(), item.getContent().trim(), item.getLineNumber()));
                fmtItem.addOne(); // i know there is at least one tag already so here i increment 1 to the value
                FormattedItem.addOneToTotalNumberOfTaskTags(); // adding to the total number of task tags already recorded...
                firstFmtItem = false;
                break;
            }
        }
        // no grouping was performed so this must be the first task tag found in the file.
        if (firstFmtItem) {
            // here i am using the string format method for my convienence as this is a nice clean way to format a string
            fi.setValue(String.format(Configuration.HTML_FORMAT_ITEM_1, item.getFileName(), item.getOrigin().trim(), item.getType(), item.getContent().trim(), item.getLineNumber()));
            fi.setNumTaskTags(1); // set one to this formatted item
            FormattedItem.addOneToTotalNumberOfTaskTags(); // adding to the total number of task tags already recorded...
        } else {
            return null; // return null because this item was grouped with a previous formatted item.
        }
        return fi;
    }
}
