package com.omo.tools.ant.writers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.omo.tools.ant.format.FormattedItem;


public class XMLItemWriter implements ItemWriter {

	public void write(OutputStream out, List<FormattedItem> itemsList) {
		try {
			out.write("<?xml version=\"1.0\" ?>".getBytes());
			out.write("<items>".getBytes());
			for (FormattedItem item : itemsList) {
				try {
					String value = item.getValue();
					out.write(value.getBytes());
				} catch (IOException e) {
					throw new RuntimeException(String.format("IO error when writing item '%s'", item), e);
				}
			}
			out.write("</items>".getBytes());
		} catch (IOException e) {
			throw new RuntimeException(String.format("IO error when writing item to XML"), e);
		}
	}

}

