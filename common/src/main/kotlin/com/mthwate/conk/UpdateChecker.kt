package com.mthwate.conk

/**
 * @author mthwate
 */
class UpdateChecker {

	private var lastUpdated: Long = 0

	init {
		update()
	}

	fun hasUpdated(lastCheck: Long): Boolean {
		return lastCheck <= lastUpdated
	}

	fun update() {
		lastUpdated = System.nanoTime()
	}

}