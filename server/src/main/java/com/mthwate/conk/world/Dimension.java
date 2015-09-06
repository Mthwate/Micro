package com.mthwate.conk.world;

import com.mthwate.conk.PropUtils;
import com.mthwate.conk.block.Block;
import com.mthwate.conk.world.generator.WorldGenerator;
import com.mthwate.datlib.math.set.Set3i;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class Dimension {

	private final String name;

	private final WorldGenerator generator;

	private Map<Set3i, Chunk> chunks = new HashMap<>();

	private static final File WORLD_DIR = new File(PropUtils.getWorldDir());

	public Dimension(String name, WorldGenerator generator) {
		this.name = name;
		this.generator = generator;
	}

	public Block getBlock(Set3i pos) {
		return getBlock(pos.getX(), pos.getY(), pos.getZ());
	}

	public Block getBlock(int x, int y, int z) {
		int size = Chunk.CHUNK_SIZE;
		return getChunk(x / size, y / size, z / size).get(x % size, y % size, z % size);
	}

	private Chunk getChunk(Set3i pos) {
		return getChunk(pos.getX(), pos.getY(), pos.getZ());
	}

	private Chunk getChunk(int x, int y, int z) {
		Set3i pos = new Set3i(x, y, z);
		Chunk chunk = chunks.get(pos);
		if (chunk == null) {
			File file = new File(getDimensionDir(), x + "." + y + "." + z + ".json");
			if (file.exists()) {
				chunk = SaveUtils.loadChunk(file);
			}
			if (chunk == null) {
				chunk = generator.genChunk(x, y, z);
				SaveUtils.saveChunk(chunk, file);
			}
			chunks.put(pos, chunk);
		}
		return chunk;

	}

	private File getDimensionDir() {
		return new File(WORLD_DIR, name);
	}
}
