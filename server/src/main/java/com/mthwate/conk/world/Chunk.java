package com.mthwate.conk.world;

import com.mthwate.conk.PropUtils;
import com.mthwate.conk.block.Block;

/**
 * @author mthwate
 */
public class Chunk {

	public static final int CHUNK_SIZE = PropUtils.getChunkSize();

	private Block[][][] blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];

	public Block get(int x, int y, int z) {
		return blocks[x][y][z];
	}

	public void set(Block block, int x, int y, int z) {
		blocks[x][y][z] = block;
	}

}
