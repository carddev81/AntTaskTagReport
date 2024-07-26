package com.omo.tools.ant.parsers;

import com.omo.tools.ant.Item;

public interface ItemParser {

	/**
	 * Parse the item from the given line and return an instance
	 * @param line
	 * @return an item, or null if none found
	 */
	Item parse(String line);
	
	/**
	 * Return true if the given line contains items this parser is interested in
	 * @param line the line to parse
	 * @return true if contains relevant items; otherwise false
	 */
	boolean containsItem(String line);
}

