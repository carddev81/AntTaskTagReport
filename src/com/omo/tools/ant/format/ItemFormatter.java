package com.omo.tools.ant.format;

import com.omo.tools.ant.Item;

/**
 * An interface defining simple methods for writing Strings in a specific format.
 */
public interface ItemFormatter {

	/**
	 * Format str to the designated format and return
	 * @param fmt - the Format constant defining what to convert to
	 * @param item - the item to convert
	 * @return the wrapped converted item as well as the original item and its format, in a FormattedItem instance
	 **/
	FormattedItem format(Format fmt, Item item); 
}

