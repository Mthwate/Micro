package com.mthwate.conk;

/**
 * @author mthwate
 */
public class UpdateChecker {

	private long lastUpdated;

	public UpdateChecker() {
		update();
	}

	public boolean hasUpdated(long lastCheck) {
		return lastCheck <= lastUpdated;
	}

	public void update() {
		lastUpdated = System.nanoTime();
	}

}
