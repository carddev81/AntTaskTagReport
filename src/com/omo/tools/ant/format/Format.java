package com.omo.tools.ant.format;
/**
 * The type of output format
 * 
 * Modified Comment: I added another format of html...as the csv, xml, and defualt of plain text did not
 * give me a nice report...I added the format of html to be able to layout a report more pleasing to the 
 * eye and easier to navigate and understand.
 * 
 * @author modified by Richard Salas, 
 **/
public enum Format {
	//TODO: can we add JSON or AIML to this?!
	HTML("HTML"),
	CSV("CSV"),
	XML("XML"),
	DEFAULT("DEFAULT");

	private String type;

	private Format(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	/**
	 * Returns one of <code>values()</code> if <code>type</code> is valid, other wise DEFAULT.
	 * @param type
	 * @return DEFAULT or <code>type</code> e.g. XML
	 */
	public static Format fromString(String type) {
		for (Format f : values()) {
			if (f.type.equalsIgnoreCase(type)) {
				return f;
			}
		}
		return DEFAULT;
	}
}

