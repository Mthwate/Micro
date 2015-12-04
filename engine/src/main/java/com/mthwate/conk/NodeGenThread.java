package com.mthwate.conk;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.mthwate.datlib.math.vector.Vector3i;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mthwate
 */
public class NodeGenThread extends Thread implements Closeable {

	private final AssetManager assetManager;
	private final World world;

	private boolean run = true;

	private final List<Task> tasks = Collections.synchronizedList(new ArrayList<Task>());

	private final Map<Vector3i, Node> done = new HashMap<>();

	public NodeGenThread(AssetManager assetManager, World world) {
		this.assetManager= assetManager;
		this.world = world;
	}

	@Override
	public void run() {
		while (run) {
			if (!tasks.isEmpty()) {
				Task task = tasks.get(0);
				tasks.remove(task);
				Node node = ChunkUtils.genNode(task.chunk, assetManager, world, task.pos, task.light);
				synchronized (this.done) {
					done.put(task.pos, node);
				}
			}
		}
	}

	public void add(Chunk chunk, Vector3i pos, List<LightChunk> light) {
		tasks.add(new Task(chunk, pos, light));
	}

	public Map<Vector3i, Node> getDone() {
		Map<Vector3i, Node> done = new HashMap<>();
		synchronized (this.done) {
			for (Map.Entry<Vector3i, Node> entry : this.done.entrySet()) {
				done.put(entry.getKey(), entry.getValue());
			}
		}
		for (Vector3i pos : done.keySet()) {
			this.done.remove(pos);
		}
		return done;
	}

	public boolean isEmpty() {
		return tasks.isEmpty() && done.isEmpty();
	}

	private static class Task {

		private final Chunk chunk;
		private final Vector3i pos;
		private final List<LightChunk> light;

		private Task(Chunk chunk, Vector3i pos, List<LightChunk> light) {
			this.chunk = chunk;
			this.pos = pos;
			this.light = light;
		}

	}

	@Override
	public void close() {
		run = false;
	}

}
