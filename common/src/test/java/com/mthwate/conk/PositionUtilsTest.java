package com.mthwate.conk;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author mthwate
 */
public class PositionUtilsTest {

	@Test
	public void test() {
		for (int size = 1; size <= 16; size++) {
			for (int i = -100; i <= 100; i++) {
				Assert.assertEquals(i, PositionUtils.getChunkFromGlobal(i, size) * size + PositionUtils.getLocalFromGlobal(i, size));
			}
		}
	}
}