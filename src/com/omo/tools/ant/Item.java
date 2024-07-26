package com.omo.tools.ant;

/**
 * Wraps a TODO or FIXME
 *
 * Modified Comment: I added another ItemType of CLEANME as these may be found in the application code.
 * I also added a fileName field into the Item class as I need this for the html report. 
 * @author modified by Richard Salas
 */
public class Item {

    /**
     * Abstract symbol denoting different forms of items
     */
    public enum ItemType {
        //TODO: perhaps utilise this inside the parsers to abstract further. Would allow for adding support to new tags by just adding to this enum
        TODO,
        FIXME,
        CLEANME;
    }

    private String fileName;
    private String origin;
    private String content;
    private int lineNumber;
    private ItemType type;

    /**
     * Construct an instance with the given origin and content
     *
     * @param origin    of the TODO or FIXME: the source file's name
     * @param content the actual content of the TODO or FIXME
     */
    public Item(String origin, String content) {
        this.origin = origin;
        this.content = content;
    }

    public Item() {
    }

    
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    
    public String getOrigin() {
        return origin;
    }

    public String getContent() {
        return content;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Item{" +
                " fileName='" + fileName + '\'' +
                " origin='" + origin + '\'' +
                ", content='" + content + '\'' +
                ", lineNumber=" + lineNumber +
                ", type=" + type +
                '}';
    }
}

