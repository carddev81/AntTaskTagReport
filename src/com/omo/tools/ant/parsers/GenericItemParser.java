package com.omo.tools.ant.parsers;

import com.omo.tools.ant.Item;

/**
 * This class parses the task tags from file into an Item object based upon TODO, FIXME, and CLEANME tags.
 * 
 * I added more filtering to this parser so that it doesn't recognize any unwanted todos...also added the cleanme tag
 * @author modified by Richard Salas
 */
public class GenericItemParser implements ItemParser {

	public Item parse(String line) {
		//The content of the todo, e.g. "Refactor" in TODO Refactor
		String content = "";
		Item item = new Item();

		//TODO: this was a "simplest implementation" quick hack; make this more scalable and elegant
		// It allowed me to unit test and prove it was possible, but it's not brilliant!
		//We do tests on the upper-case lines, so we include lower case TODOs, FIXMEs, or CLEANMES;
		//The substrings, however, are performed on the original line so the comments case is preserved
		String upperCaseLine = line.toUpperCase();
		if (upperCaseLine.contains("TODO")) {
			item.setType(Item.ItemType.TODO);
			content = line.substring(upperCaseLine.indexOf("TODO") + 4);
		} else if (upperCaseLine.contains("FIXME")) {
			item.setType(Item.ItemType.FIXME);
			content = line.substring(upperCaseLine.indexOf("FIXME") + 5);
		} else if (upperCaseLine.contains("CLEANME")) {
			item.setType(Item.ItemType.CLEANME);
			content = line.substring(upperCaseLine.indexOf("CLEANME") + 7);
		}
		
		if ("".equals(content.trim())) {
		    item.setContent("No description was found, but this is a valid Task and needs to be checked.");
		} else {
		    item.setContent(content);
		}
		return item;
	}

	/**
	 * Check to see if this line really contains a task tag...
	 */
	public boolean containsItem(String line) {
	    String lineUpperCase = line.toUpperCase();

	    // more filtering done initially...we do not want unnecessary todos in code.
	    if (line.contains("Todo") || line.contains("ToDo") || lineUpperCase.contains("TODOS")) {
		    return false;
		} 
		return (line.contains("//") || line.contains("/*") || line.contains("#") || line.startsWith("*") || line.contains("<!--")) &&
				(lineUpperCase.contains("TODO") || lineUpperCase.contains("FIXME") || lineUpperCase.contains("CLEANME"));
	}

}

