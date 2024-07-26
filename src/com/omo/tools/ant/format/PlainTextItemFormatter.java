package com.omo.tools.ant.format;

import com.omo.tools.ant.Item;

public class PlainTextItemFormatter implements ItemFormatter {

	public FormattedItem format(Format fmt, Item i) {
		FormattedItem fi = new FormattedItem();
		fi.setItem(i);
		fi.setFormat(fmt);
		
		fi.setValue(String.format("%s[%s][%s]: %s", i.getOrigin().trim(), i.getLineNumber(), i.getType(), i.getContent().trim()));
		
		return fi;
	}

}

