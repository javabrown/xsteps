package com.jbrown.util;

import java.util.Enumeration;
import java.util.Locale;

public class ResourceBundle {
	static java.util.ResourceBundle _rb = java.util.ResourceBundle.getBundle(
			"com.jbrown.resources", Locale.US);
	static Enumeration<String> keys = _rb.getKeys();

	public static String getProperty(String propName) {
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = _rb.getString(key);

			if (key.equalsIgnoreCase(propName)) {
				return value;
			}
		}

		return "";
	}

}
