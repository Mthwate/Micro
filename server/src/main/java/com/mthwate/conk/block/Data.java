package com.mthwate.conk.block;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class Data {

	private Map<String, Object> data = new HashMap<>();

	public void set(String key, Object value) {
		data.put(key, value);
	}

	public String getString(String key) {
		String str = null;
		Object obj = data.get(key);
		if (obj instanceof String) {
			str = (String) obj;
		}
		return str;
	}

	public Integer getInt(String key) {
		Integer num = null;
		Object obj = data.get(key);
		if (obj instanceof Integer) {
			num = (Integer) obj;
		}
		return num;
	}

}
