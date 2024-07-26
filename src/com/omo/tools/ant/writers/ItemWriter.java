package com.omo.tools.ant.writers;

import java.io.OutputStream;
import java.util.List;

import com.omo.tools.ant.format.FormattedItem;


/**
 * Responsible for writing to the given output stream
 */
public interface ItemWriter {
	/**
	 * Write all the items to the given output stream
	 * @param out the stream to write to
	 * @param itemsList the items to write
	 */
	void write(OutputStream out, List<FormattedItem> itemsList);
}

