package com.mthwate.conk.world.generator;

import com.mthwate.conk.world.Chunk;
import com.mthwate.datlib.math.set.Set3i;

/**
 * @author mthwate
 */
public abstract class WorldGenerator {

	public Chunk genChunk(Set3i pos) {
		return genChunk(pos.getX(), pos.getY(), pos.getZ());
	}

	public abstract Chunk genChunk(int x, int y, int z);

}
