package com.mthwate.conk;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.mthwate.conk.info.BlockInfo;
import com.mthwate.datlib.math.set.Set3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author mthwate
 */
public class World extends AbstractAppState {

	private static final Logger log = LoggerFactory.getLogger(World.class);

	private static final int CHUNK_SIZE = 16;

	private final Map<Set3i, Chunk> chunks = Collections.synchronizedMap(new HashMap<Set3i, Chunk>());

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

		List<Set3i> updates = new ArrayList<>();

		synchronized (chunks) {
			for (Map.Entry<Set3i, Chunk> entry : chunks.entrySet()) {
				Chunk chunk = entry.getValue();
				if (chunk.hasChanged()) {
					updates.add(entry.getKey());

					tryAdd(updates, entry.getKey().addNew(-1, 0, 0));
					tryAdd(updates, entry.getKey().addNew(1, 0, 0));
					tryAdd(updates, entry.getKey().addNew(0, -1, 0));
					tryAdd(updates, entry.getKey().addNew(0, 1, 0));
					tryAdd(updates, entry.getKey().addNew(0, 0, -1));
					tryAdd(updates, entry.getKey().addNew(0, 0, 1));
				}
			}
		}

		for (Set3i pos : updates) {
			node.detachChildNamed(pos.toString());
			Node chunkNode = chunks.get(pos).genNode(assetManager, this, pos.clone());
			if (chunkNode.getChildren().size() > 0) {
				node.attachChild(chunkNode);
			}
		}
	}

	private void tryAdd(List<Set3i> updates, Set3i pos) {
		if (chunks.containsKey(pos)) {
			updates.add(pos);
		}
	}

	public void setBlock(int x, int y, int z, BlockInfo block) {
		Set3i pos = new Set3i(getChunk(x), getChunk(y), getChunk(z));

		synchronized (chunks) {
			Chunk chunk = chunks.get(pos);
			if (chunk == null) {
				chunk = new Chunk(CHUNK_SIZE);
				chunks.put(pos, chunk);
			}
			chunk.setBlock(getLocal(x), getLocal(y), getLocal(z), block);
		}
	}

	public BlockInfo getBlock(int x, int y, int z) {
		Set3i pos = new Set3i(getChunk(x), getChunk(y), getChunk(z));

		BlockInfo block;

		synchronized (chunks) {
			Chunk chunk = chunks.get(pos);
			if (chunk == null) {
				chunk = new Chunk(CHUNK_SIZE);
				chunks.put(pos, chunk);
			}
			block = chunk.getBlock(getLocal(x), getLocal(y), getLocal(z));
		}

		return block;
	}

	private static int getLocal(int i) {
		int n = i;

		if (n < 0) {
			n -= CHUNK_SIZE - 1;
		}

		n %= CHUNK_SIZE;

		if (i < 0) {
			n = CHUNK_SIZE + n - 1;
		}

		return n;
	}

	private static int getChunk(int i) {
		if (i < 0) {
			i -= CHUNK_SIZE - 1;
		}

		i /= CHUNK_SIZE;

		return i;
	}
}
