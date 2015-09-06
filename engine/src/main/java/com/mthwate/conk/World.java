package com.mthwate.conk;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.mthwate.conk.info.BlockInfo;
import com.mthwate.datlib.math.set.Set3i;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mthwate
 */
public class World extends AbstractAppState {

	private static final int CHUNK_SIZE = 16;

	private final Map<Set3i, Chunk> chunks = new ConcurrentHashMap<>();

	private final Node node = new Node();

	private AssetManager assetManager;

	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		assetManager = app.getAssetManager();
	}

	public Node getNode() {
		return node;
	}

	@Override
	public void update(float tpf) {
		for (Map.Entry<Set3i, Chunk> entry : chunks.entrySet()) {
			Chunk chunk = entry.getValue();
			if (chunk.hasChanged()) {
				Set3i pos = entry.getKey();
				node.detachChildNamed(pos.toString());
				Node chunkNode = chunk.genNode(assetManager, this, pos);
				if (chunkNode.getChildren().size() > 0) {
					node.attachChild(chunkNode);
				}
			}
		}
	}

	public void setBlock(int x, int y, int z, BlockInfo block) {

		int tx = x;
		int ty = y;
		int tz = z;

		if (x < 0) {
			tx -= CHUNK_SIZE - 1;
		}
		if (y < 0) {
			ty -= CHUNK_SIZE - 1;
		}
		if (z < 0) {
			tz -= CHUNK_SIZE - 1;
		}

		int cx = tx / CHUNK_SIZE;
		int cy = ty / CHUNK_SIZE;
		int cz = tz / CHUNK_SIZE;

		Set3i pos = new Set3i(cx, cy, cz);

		Chunk chunk = chunks.get(pos);
		if (chunk == null) {
			chunk = new Chunk(CHUNK_SIZE);
			chunks.put(pos, chunk);
		}


		tx %= CHUNK_SIZE;
		ty %= CHUNK_SIZE;
		tz %= CHUNK_SIZE;

		if (x < 0) {
			tx = CHUNK_SIZE - tx - 1;
		}
		if (y < 0) {
			ty = CHUNK_SIZE - ty - 1;
		}
		if (z < 0) {
			tz = CHUNK_SIZE - tz - 1;
		}

		chunk.setBlock(tx, ty, tz, block);
	}

	public BlockInfo getBlock(int x, int y, int z) {

		int tx = x;
		int ty = y;
		int tz = z;

		if (x < 0) {
			tx -= CHUNK_SIZE - 1;
		}
		if (y < 0) {
			ty -= CHUNK_SIZE - 1;
		}
		if (z < 0) {
			tz -= CHUNK_SIZE - 1;
		}

		int cx = tx / CHUNK_SIZE;
		int cy = ty / CHUNK_SIZE;
		int cz = tz / CHUNK_SIZE;

		Set3i pos = new Set3i(cx, cy, cz);

		Chunk chunk = chunks.get(pos);
		if (chunk == null) {
			chunk = new Chunk(CHUNK_SIZE);
			chunks.put(pos, chunk);
		}


		tx %= CHUNK_SIZE;
		ty %= CHUNK_SIZE;
		tz %= CHUNK_SIZE;

		if (x < 0) {
			tx = CHUNK_SIZE - tx - 1;
		}
		if (y < 0) {
			ty = CHUNK_SIZE - ty - 1;
		}
		if (z < 0) {
			tz = CHUNK_SIZE - tz - 1;
		}

		return chunk.getBlock(tx, ty, tz);
	}
}
