package com.mthwate.conk;

import com.mthwate.datlib.PropertyUtils;

import java.io.File;

/**
 * @author mthwate
 */
public class PropUtils {

	private static final File FILE = new File("client.properties");

	private static String getProperty(String key, String defaultValue) {
		return PropertyUtils.getProperty(FILE, key, defaultValue);
	}

	public static String getIp() {
		return getProperty("ip", "localhost");
	}

}
