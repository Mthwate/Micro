package com.mthwate.conk.world.generator;

import com.mthwate.conk.block.Block;
import com.mthwate.conk.block.BlockStore;
import com.mthwate.conk.world.Chunk;
import com.mthwate.datlib.math.vector.Vector3i;

import java.util.List;
import java.util.Random;

/**
 * @author mthwate
 */
public class RandFlatWorldGenerator extends FlatWorldGenerator {

	private Random rand = new Random();

	@Override
	public Chunk genChunk(Vector3i chunkPos) {
		Chunk chunk = super.genChunk(chunkPos);

		for (int ix = 0; ix < Chunk.CHUNK_SIZE; ix++) {
			for (int iy = 0; iy < Chunk.CHUNK_SIZE; iy++) {
				for (int iz = 0; iz < Chunk.CHUNK_SIZE; iz++) {
					if (rand.nextInt(100) == 0) {
						List<Block> blocks = BlockStore.getAllBlocks();
						chunk.set(blocks.get(rand.nextInt(blocks.size())), ix, iy, iz);
					}
				}
			}
		}

		return chunk;
	}

}
