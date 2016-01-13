package com.mthwate.conk.world;

import com.jme3.scene.Node;
import com.mthwate.conk.PositionUtils;
import com.mthwate.conk.PropUtils;
import com.mthwate.conk.block.Block;
import com.mthwate.conk.world.generator.WorldGenerator;
import com.mthwate.datlib.math.vector.Vector3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mthwate
 */
public class Dimension {

	private final WorldGenerator generator;

	private Map<Vector3i, Chunk> chunks = new HashMap<>();

	private static final File WORLD_DIR = new File(PropUtils.getWorldDir());

	private static final Logger log = LoggerFactory.getLogger(Dimension.class);

	private final File directory;

	private final ExecutorService executor = Executors.newSingleThreadExecutor();

	public Dimension(String name, WorldGenerator generator) {
		this.generator = generator;
		directory = new File(WORLD_DIR, name);
	}

	public Block getBlock(Vector3i pos) {
		return getBlock(pos.getX(), pos.getY(), pos.getZ());
	}

	public Block getBlock(int x, int y, int z) {
		int size = Chunk.CHUNK_SIZE;

		int cx = PositionUtils.INSTANCE.getChunkFromGlobal(x, size);
		int cy = PositionUtils.INSTANCE.getChunkFromGlobal(y, size);
		int cz = PositionUtils.INSTANCE.getChunkFromGlobal(z, size);

		int lx = PositionUtils.INSTANCE.getLocalFromGlobal(x, size);
		int ly = PositionUtils.INSTANCE.getLocalFromGlobal(y, size);
		int lz = PositionUtils.INSTANCE.getLocalFromGlobal(z, size);

		return getChunk(cx, cy, cz).get(lx, ly, lz);
	}

	private Chunk getChunk(Vector3i pos) {
		Chunk chunk = chunks.get(pos);
		if (chunk == null) {
			ChunkLoader loader = new ChunkLoader(pos, directory, generator);
			chunk = loader.call();
			chunks.put(pos, chunk);
		}
		return chunk;
	}

	private Chunk getChunk(int x, int y, int z) {
		return getChunk(new Vector3i(x, y, z));
	}

	public boolean hasUpdated(Vector3i pos, long lastCheck) {
		return getChunk(pos).hasUpdated(lastCheck);
	}

	public Node genNode(Vector3i pos) {
		return getChunk(pos).genNode(pos);
	}

	public void setBlock(Block block, Vector3i pos) {
		setBlock(block, pos.getX(), pos.getY(), pos.getZ());
	}

	public void setBlock(Block block, int x, int y, int z) {
		int size = Chunk.CHUNK_SIZE;

		int cx = PositionUtils.INSTANCE.getChunkFromGlobal(x, size);
		int cy = PositionUtils.INSTANCE.getChunkFromGlobal(y, size);
		int cz = PositionUtils.INSTANCE.getChunkFromGlobal(z, size);

		int lx = PositionUtils.INSTANCE.getLocalFromGlobal(x, size);
		int ly = PositionUtils.INSTANCE.getLocalFromGlobal(y, size);
		int lz = PositionUtils.INSTANCE.getLocalFromGlobal(z, size);

		getChunk(cx, cy, cz).set(block, lx, ly, lz);
	}

}
