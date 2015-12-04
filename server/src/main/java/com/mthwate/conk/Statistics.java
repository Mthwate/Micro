package com.mthwate.conk;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class Statistics {

	private final Map<String, Object> statistics = new HashMap<>();

	public void set(String name, Object value) {
		statistics.put(name, value);
	}

	public <T> T get(String name, T defaultValue) {
		T value = (T) statistics.get(name);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}

	public void add(String name, int value) {
		statistics.put(name, get(name, 0) + value);
	}

	public void add(String name, long value) {
		statistics.put(name, get(name, 0L) + value);
	}

	public void clear() {
		statistics.clear();
	}

	@Override
	public String toString() {
		return statistics.toString();
	}
}
