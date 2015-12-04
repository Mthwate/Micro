package com.mthwate.conk;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.mthwate.conk.info.BlockInfo;
import com.mthwate.datlib.math.vector.Vector3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mthwate
 */
public class World extends AbstractAppState implements Closeable {

	private static final Logger log = LoggerFactory.getLogger(World.class);

	private static final int CHUNK_SIZE = 16;//TODO remove this

	private final Map<Vector3i, Chunk> chunks = Collections.synchronizedMap(new HashMap<Vector3i, Chunk>());

	private final Map<Vector3i, LightMap> lightMaps = Collections.synchronizedMap(new HashMap<Vector3i, LightMap>());

	private final Node node = new Node();

	private NodeGenThread nodeGenThread;

	private long lastTime = 0;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		nodeGenThread = new NodeGenThread(app.getAssetManager(), this);
		nodeGenThread.start();
	}

	public Node getNode() {
		return node;
	}

	@Override
	public void update(float tpf) {

		if (nodeGenThread.isEmpty()) {

			long now = System.nanoTime();

			List<Vector3i> updates = new ArrayList<>();

			synchronized (chunks) {
				for (Map.Entry<Vector3i, Chunk> entry : chunks.entrySet()) {
					Chunk chunk = entry.getValue();
					if (chunk.hasUpdated(lastTime)) {
						updates.add(entry.getKey());

						tryAdd(updates, entry.getKey().add(-1, 0, 0));
						tryAdd(updates, entry.getKey().add(1, 0, 0));
						tryAdd(updates, entry.getKey().add(0, -1, 0));
						tryAdd(updates, entry.getKey().add(0, 1, 0));
						tryAdd(updates, entry.getKey().add(0, 0, -1));
						tryAdd(updates, entry.getKey().add(0, 0, 1));
					}
				}
			}

			lastTime = now;

			for (LightMap map : lightMaps.values()) {
				for (Vector3i pos : updates) {
					if (!map.isInit() || map.contains(pos)) {
						map.generate(this);
						break;
					}
				}
			}

			for (Vector3i pos : updates) {

				List<LightChunk> light = new ArrayList<>();

				for (LightMap map : lightMaps.values()) {
					if (map.contains(pos)) {
						light.add(map.getChunk(pos));
					}
				}

				nodeGenThread.add(chunks.get(pos), pos, light);
			}

		} else {

			for (Map.Entry<Vector3i, Node> entry : nodeGenThread.getDone().entrySet()) {
				node.detachChildNamed(entry.getKey().toString());
				if (entry.getValue().getChildren().size() > 0) {
					node.attachChild(entry.getValue());
				}
			}
		}
	}

	private void tryAdd(List<Vector3i> updates, Vector3i pos) {
		if (chunks.containsKey(pos)) {
			updates.add(pos);
		}
	}

	public void setBlock(int x, int y, int z, BlockInfo block) {
		Vector3i cPos = new Vector3i(getChunk(x), getChunk(y), getChunk(z));

		synchronized (chunks) {
			Chunk chunk = chunks.get(cPos);
			if (chunk == null) {
				chunk = new Chunk(CHUNK_SIZE);
				chunks.put(cPos, chunk);
			}
			chunk.setBlock(getLocal(x), getLocal(y), getLocal(z), block);
		}

		synchronized (lightMaps) {
			Vector3i gPos = new Vector3i(x, y, z);

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

	public BlockInfo getBlock(Vector3i pos) {
		return getBlock(pos.getX(), pos.getY(), pos.getZ());
	}

	public BlockInfo getBlock(int x, int y, int z) {
		Vector3i pos = new Vector3i(getChunk(x), getChunk(y), getChunk(z));

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
		return PositionUtils.getLocalFromGlobal(i, CHUNK_SIZE);
	}

	private static int getChunk(int i) {
		return PositionUtils.getChunkFromGlobal(i, CHUNK_SIZE);
	}

	public void updateMarkChunk(Vector3i pos) {
		chunks.get(pos).markUpdated();
	}

	@Override
	public void close() {
		nodeGenThread.close();
	}
}
