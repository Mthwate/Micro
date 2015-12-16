package com.mthwate.conk.world.generator;

import com.mthwate.conk.block.Block;
import com.mthwate.conk.block.BlockStore;
import com.mthwate.conk.world.Chunk;
import com.mthwate.datlib.math.vector.Vector3i;

/**
 * @author mthwate
 */
public class FlatWorldGenerator implements WorldGenerator {

	@Override
	public Chunk genChunk(Vector3i chunkPos) {
		Chunk chunk = new Chunk();//TODO change this;

		for (int ix = 0; ix < Chunk.CHUNK_SIZE; ix++) {
			for (int iy = 0; iy < Chunk.CHUNK_SIZE; iy++) {
				for (int iz = 0; iz < Chunk.CHUNK_SIZE; iz++) {
					Block block;
					if (chunkPos.getY() < 0) {
						block = BlockStore.getBlock("topsoil");
					} else {
						block = BlockStore.getBlock("air");
					}
					chunk.set(block, ix, iy, iz);
				}
			}
		}

		return chunk;
	}

}
