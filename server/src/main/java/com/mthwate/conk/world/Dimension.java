package com.mthwate.conk.world;

import com.jme3.scene.Node;
import com.mthwate.conk.PositionUtils;
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

		int cx = PositionUtils.getChunk(x, Chunk.CHUNK_SIZE);
		int cy = PositionUtils.getChunk(y, Chunk.CHUNK_SIZE);
		int cz = PositionUtils.getChunk(z, Chunk.CHUNK_SIZE);

		int lx = PositionUtils.getLocal(x, Chunk.CHUNK_SIZE);
		int ly = PositionUtils.getLocal(y, Chunk.CHUNK_SIZE);
		int lz = PositionUtils.getLocal(z, Chunk.CHUNK_SIZE);

		return getChunk(cx, cy, cz).get(lx, ly, lz);
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

	public void setChanged(Set3i pos, boolean changed) {
		getChunk(pos).setChanged(changed);
	}

	public boolean hasChanged(Set3i pos) {
		return getChunk(pos).hasChanged();
	}

	public Node genNode(Set3i pos) {
		return getChunk(pos).genNode(pos);
	}
}
