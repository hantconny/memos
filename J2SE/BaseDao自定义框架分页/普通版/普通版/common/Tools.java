package com.common;

import java.io.IOException;
import java.util.Properties;

public class Tools extends Properties {
	private static Tools tools;

	private Tools() {
	}

	private static synchronized void init(String fileName) {
		if (null == tools) {
			tools = new Tools();
		}
		try {
			tools.load(Tools.class.getResourceAsStream("/" + fileName
					+ ".properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getValue(String key) {
		return this.getProperty(key);
	}

	public static synchronized Tools newInstance(String fileName) {
		init(fileName);
		return tools;
	}
}
