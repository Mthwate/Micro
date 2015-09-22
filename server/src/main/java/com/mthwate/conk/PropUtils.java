package com.mthwate.conk;

import com.mthwate.datlib.PropertyUtils;

import java.io.File;

/**
 * @author mthwate
 */
public class PropUtils {

	private static final File FILE = new File("server.properties");

	private static String getProperty(String key, String defaultValue) {
		return PropertyUtils.getProperty(FILE, key, defaultValue);
	}

	private static int getProperty(String key, int defaultValue) {
		return Integer.parseInt(getProperty(key, String.valueOf(defaultValue)));
	}

	private static float getProperty(String key, float defaultValue) {
		return Float.parseFloat(getProperty(key, String.valueOf(defaultValue)));
	}

	public static String getUserDir() {
		return getProperty("userDir", "users");
	}

	public static String getWorldDir() {
		return getProperty("worldDir", "world");
	}

	public static int getChunkSize() {
		return getProperty("chunkSize", 16);
	}

	public static float getPlayerHeight() {
		return getProperty("playerHeight", 1.75f);
	}

	public static float getPlayerRadius() {
		return getProperty("playerRadius", 0.23f);
	}

	public static float getPlayerMass() {
		return getProperty("playerMass", 80f);
	}

	public static float getCubeSize() {
		return getProperty("cubeSize", 1f);
	}

}
