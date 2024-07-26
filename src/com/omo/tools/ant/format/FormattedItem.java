package com.omo.tools.ant.format;


import java.util.HashMap;
import java.util.Map;

import com.omo.tools.ant.Item;

/**
 * Wraps a formatted String...
 * 
 * Modified by implementing Comparable interface for sorting a list by the number of Task tags per formatted item.
 * Also aded static field to count the total number of task tags and also to count number of task tags.
 * 
 * @author modified by Richard Salas
 **/
public class FormattedItem implements Comparable<FormattedItem> {

    private static int totalNumberOfTaskTags;
    
    private int numTaskTags;
    private String value;
    private Format format;
    private Item item;
    private Map<String, Object> properties = new HashMap<String, Object>();

    static {
        totalNumberOfTaskTags = 0;
    }
    
    /**
     * This method increments the totalNumberOfTaskTags by 1.
     */
    public static void addOneToTotalNumberOfTaskTags() {
        totalNumberOfTaskTags++;
    }
    
    /**
     * 
     * @return totalNumberOfTaskTags - int value of the total number of task tags
     */
    public static int getTotalNumberOfTaskTags() {
        return totalNumberOfTaskTags;
    }

    /**
     * @param numTaskTags the numTaskTags to set
     */
    public void setNumTaskTags(int numTaskTags) {
        this.numTaskTags = numTaskTags;
    }

    /**
     * This method increments the numTaskTags by 1.
     */
    public void addOne() {
        numTaskTags++;
    }
    
    /**
     * @return the numTaskTags
     */
    public int getNumTaskTags() {
        return numTaskTags;
    }

    /**
     * @return item - the item object containing file name, path, ect...
     */
    public Item getItem() {
        return item;
    }

    /**
     * Setter for the item object
     * @param item - item object
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Setter for the value
     * @param value - string value
     */
    public void setValue(String value) {
        this.value = value;
    }

    
    /**
     * Setter for the format
     * @param format - format object to set
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    /**
     * @return format - format object
     */
    public Format getFormat() {
        return this.format;
    }

    /**
     * @return String - value  
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Setter for properties
     * @param key - string value
     * @param val - object value 
     */
    public void setProperty(String key, Object val) {
        this.properties.put(key, val);
    }

    /**
     * Getter for properties
     * @param key - value to use for getting the property obect
     * @return Object - the object to return
     */
    public Object getProperty(String key) {
        return this.properties.get(key);
    }

    /**
     * This method adds a closing html table <code>&lt;/table&gt;</code> tag to the value. 
     * @param valueToAppend
     */
    public void appendToValue(String valueToAppend) {
        if (this.value.contains("</table>")) {
            this.value = this.value.replace("</table>", "");
        }
        this.value += valueToAppend;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("FormattedItem[%s, %s, %s, '%s']", format, item, value, properties);
    }

    /**
     * Implemented method from the {@link Comparable} interface for sorting by number of task tags.
     * 
     * @return a negative integer, zero, or a positive integer as this object
     *      is less than, equal to, or greater than the specified object.
     */
    public int compareTo(FormattedItem fmt) {
        return Integer.valueOf(fmt.getNumTaskTags()).compareTo(Integer.valueOf(this.getNumTaskTags()));
    }

}

