package com.omo.tools.ant.writers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.omo.tools.ant.Configuration;
import com.omo.tools.ant.format.FormattedItem;


public class PlainTextItemWriter implements ItemWriter {

	public void write(OutputStream out, List<FormattedItem> itemsList) {
		if (out == null || itemsList == null) throw new IllegalArgumentException("output stream or items list was null");
		
		for (FormattedItem item : itemsList) {
			try {
				String value = item.getValue() + Configuration.LINE_SEP;
				out.write(value.getBytes());
			} catch (IOException e) {
				throw new RuntimeException(String.format("IO error when writing item '%s'", item), e);
			}
		}
	}

}

