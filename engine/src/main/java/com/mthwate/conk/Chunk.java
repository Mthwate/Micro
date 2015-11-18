package com.mthwate.conk;

import com.mthwate.conk.info.BlockInfo;

/**
 * @author mthwate
 */
public class Chunk {

	private final int size;

	private final BlockInfo[][][] blocks;

	private final UpdateChecker updateChecker = new UpdateChecker();

	public Chunk(int size) {
		this.size = size;
		blocks = new BlockInfo[size][size][size];
	}

	public void setBlock(int x, int y, int z, BlockInfo block) {
		blocks[x][y][z] = block;
		markUpdated();
	}

	public boolean hasUpdated(long lastCheck) {
		return updateChecker.hasUpdated(lastCheck);
	}

	public void markUpdated() {
		updateChecker.update();
	}

	public BlockInfo getBlock(int x, int y, int z) {
		return blocks[x][y][z];
	}

	public int getSize() {
		return size;
	}
}
