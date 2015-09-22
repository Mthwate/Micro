package com.mthwate.conk.world.generator;

import com.mthwate.conk.block.Block;
import com.mthwate.conk.block.BlockStore;
import com.mthwate.conk.world.Chunk;

import java.util.List;
import java.util.Random;

/**
 * @author mthwate
 */
public class RandomWorldGenerator extends WorldGenerator {

	@Override
	public Chunk genChunk(int x, int y, int z) {
		Chunk chunk = new Chunk();//TODO change this;

		for (int ix = 0; ix < Chunk.CHUNK_SIZE; ix++) {
			for (int iy = 0; iy < Chunk.CHUNK_SIZE; iy++) {
				for (int iz = 0; iz < Chunk.CHUNK_SIZE; iz++) {
					chunk.set(randBlock(), ix, iy, iz);
				}
			}
		}

		return chunk;
	}

	private static Block randBlock() {
		Random rand = new Random();//TODO replace this
		Block block = BlockStore.getBlock("air");
		if (rand.nextInt(5) == 0) {
			List<Block> blocks = BlockStore.getAllBlocks();
			block = blocks.get(rand.nextInt(blocks.size()));
		}
		return block;
	}

}
