package com.mthwate.conk;

/**
 * @author mthwate
 */
public class PositionUtils {

	public static int getLocal(int i, int chunkSize) {
		int n = i;

		if (n < 0) {
			n -= chunkSize - 1;
		}

		n %= chunkSize;

		if (i < 0) {
			n = chunkSize + n - 1;
		}

		return n;
	}

	public static int getChunk(int i, int chunkSize) {
		if (i < 0) {
			i -= chunkSize - 1;
		}

		i /= chunkSize;

		return i;
	}

}
