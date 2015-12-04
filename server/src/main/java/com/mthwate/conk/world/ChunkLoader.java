package com.mthwate.conk.world;

import com.mthwate.conk.world.generator.WorldGenerator;
import com.mthwate.datlib.math.vector.Vector3i;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * @author mthwate
 */
public class ChunkLoader implements Callable<Chunk> {

	private final Vector3i pos;

	private final File dimensionDir;

	private final WorldGenerator generator;

	public ChunkLoader(Vector3i pos, File dimensionDir, WorldGenerator generator) {
		this.pos = pos;
		this.dimensionDir = dimensionDir;
		this.generator = generator;
	}

	@Override
	public Chunk call() {
		Chunk chunk = null;
		File file = new File(dimensionDir, pos.getX() + "." + pos.getY() + "." + pos.getZ() + ".json");
		if (file.exists()) {
			chunk = SaveUtils.loadChunk(file);
		}
		if (chunk == null) {
			chunk = generator.genChunk(pos);
			SaveUtils.saveChunk(chunk, file);
		}
		return chunk;
	}
}
