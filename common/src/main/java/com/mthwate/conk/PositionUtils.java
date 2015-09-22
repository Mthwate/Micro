package com.mthwate.conk;

import com.mthwate.datlib.math.set.Set3i;

/**
 * @author mthwate
 */
public class PositionUtils {

	public static int getLocalFromGlobal(int global, int chunkSize) {
		int n = global;

		if (n < 0) {
			n -= chunkSize - 1;
		}

		n %= chunkSize;

		if (global < 0) {
			n = chunkSize + n - 1;
		}

		return n;
	}

	public static Set3i getLocalFromGlobal(int x, int y, int z, int chunkSize) {
		int lx = getLocalFromGlobal(x, chunkSize);
		int ly = getLocalFromGlobal(y, chunkSize);
		int lz = getLocalFromGlobal(z, chunkSize);
		return new Set3i(lx, ly, lz);
	}

	public static Set3i getLocalFromGlobal(Set3i global, int chunkSize) {
		return getLocalFromGlobal(global.getX(), global.getY(), global.getZ(), chunkSize);
	}

	public static int getChunkFromGlobal(int global, int chunkSize) {
		if (global < 0) {
			global -= chunkSize - 1;
		}

		global /= chunkSize;

		return global;
	}

	public static Set3i getChunkFromGlobal(int x, int y, int z, int chunkSize) {
		int cx = getChunkFromGlobal(x, chunkSize);
		int cy = getChunkFromGlobal(y, chunkSize);
		int cz = getChunkFromGlobal(z, chunkSize);
		return new Set3i(cx, cy, cz);
	}

	public static Set3i getChunkFromGlobal(Set3i global, int chunkSize) {
		return getChunkFromGlobal(global.getX(), global.getY(), global.getZ(), chunkSize);
	}
}
