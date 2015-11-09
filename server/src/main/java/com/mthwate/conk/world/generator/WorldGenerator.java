package com.mthwate.conk.world.generator;

import com.mthwate.conk.world.Chunk;
import com.mthwate.datlib.math.vector.Vector3i;

/**
 * @author mthwate
 */
public interface WorldGenerator {

	Chunk genChunk(Vector3i chunkPos);

}
