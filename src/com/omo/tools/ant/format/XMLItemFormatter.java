package com.omo.tools.ant.format;

import com.omo.tools.ant.Item;


public class XMLItemFormatter implements ItemFormatter {

	public FormattedItem format(Format fmt, Item i) {
		FormattedItem fi = new FormattedItem();
		fi.setItem(i);
		fi.setFormat(fmt);

		Item item = fi.getItem();
		fi.setValue(String.format("<item origin=\"%s\" line=\"%s\" type=\"%s\">%s</item>",
				item.getOrigin().trim(),
				item.getLineNumber(),
				item.getType(),
				item.getContent().trim()));

		return fi;
	}
}

