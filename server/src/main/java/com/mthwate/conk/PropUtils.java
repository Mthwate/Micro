package com.mthwate.conk;

import com.mthwate.datlib.PropertyUtils;

import java.io.File;

/**
 * @author mthwate
 */
public class PropUtils {

	private static final File CLIENT = new File("client.properties");

	private static final File SERVER = new File("server.properties");

	private static String getServerProperty(String key, String defaultValue) {
		return PropertyUtils.getProperty(SERVER, key, defaultValue);
	}

	private static int getServerProperty(String key, int defaultValue) {
		return Integer.parseInt(PropertyUtils.getProperty(SERVER, key, String.valueOf(defaultValue)));
	}

	private static float getServerProperty(String key, float defaultValue) {
		return Float.parseFloat(PropertyUtils.getProperty(SERVER, key, String.valueOf(defaultValue)));
	}

	private static String getClientProperty(String key, String defaultValue) {
		return PropertyUtils.getProperty(CLIENT, key, defaultValue);
	}

	public static String getUserDir() {
		return getServerProperty("userDir", "users");
	}

	public static String getWorldDir() {
		return getServerProperty("worldDir", "world");
	}

	public static int getChunkSize() {
		return getServerProperty("chunkSize", 16);
	}

	public static float getPlayerHeight() {
		return getServerProperty("playerHeight", 1.75f);
	}

	public static float getPlayerRadius() {
		return getServerProperty("playerRadius", 0.23f);
	}

	public static float getPlayerMass() {
		return getServerProperty("playerMass", 80f);
	}

	public static float getCubeSize() {
		return getServerProperty("cubeSize", 1f);
	}

}
