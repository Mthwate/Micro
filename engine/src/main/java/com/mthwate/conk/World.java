package com.mthwate.conk;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.mthwate.conk.info.BlockInfo;
import com.mthwate.datlib.Timer;
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

	private final Map<Set3i, LightMap> lightMaps = Collections.synchronizedMap(new HashMap<Set3i, LightMap>());

	private final Node node = new Node();

	private AssetManager assetManager;

	@Override
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

		for (LightMap map : lightMaps.values()) {
			for (Set3i pos : updates) {
				if (!map.isInit() || map.contains(this, pos)) {
					map.generate(this);
					break;
				}
			}
		}

		for (Set3i pos : updates) {

			List<LightMap.LightChunk> light = new ArrayList<>();

			for (LightMap map : lightMaps.values()) {
				if (map.contains(this, pos)) {
					light.add(map.getChunk(this, pos));
				}
			}

			Node chunkNode = chunks.get(pos).genNode(assetManager, this, pos.clone(), light);
			node.detachChildNamed(pos.toString());
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
		Set3i cPos = new Set3i(getChunk(x), getChunk(y), getChunk(z));

		synchronized (chunks) {
			Chunk chunk = chunks.get(cPos);
			if (chunk == null) {
				chunk = new Chunk(CHUNK_SIZE);
				chunks.put(cPos, chunk);
			}
			chunk.setBlock(getLocal(x), getLocal(y), getLocal(z), block);
		}

		synchronized (lightMaps) {
			Set3i gPos = new Set3i(x, y, z);

			LightMap tmpMap = lightMaps.get(gPos);

			if (tmpMap != null) {
				tmpMap.delete(this);
			}

			lightMaps.remove(gPos);

			if (block != null && block.getLight() != null) {
				lightMaps.put(gPos, new LightMap(gPos, block.getLight(), block.getLightFalloff(), CHUNK_SIZE));
			}
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
		return PositionUtils.getLocal(i, CHUNK_SIZE);
	}

	private static int getChunk(int i) {
		return PositionUtils.getChunk(i, CHUNK_SIZE);
	}

	public void updateChunk(Set3i pos) {
		chunks.get(pos).markChanged();
	}
}
