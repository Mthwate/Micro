package com.mthwate.conk.state;

import com.jme3.network.HostedConnection;
import com.mthwate.conk.PositionUtils;
import com.mthwate.conk.block.Block;
import com.mthwate.conk.message.BlockUpdateMessage;
import com.mthwate.conk.message.ClearGroupMessage;
import com.mthwate.conk.message.GroupUpdateMessage;
import com.mthwate.conk.user.User;
import com.mthwate.conk.user.UserStore;
import com.mthwate.conk.world.Chunk;
import com.mthwate.conk.world.Dimension;
import com.mthwate.datlib.math.vector.Vector3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;

/**
 * @author mthwate
 */
public class WorldUpdateProcessor implements Callable<Queue<WorldUpdate>> {

	private final Map<String, Vector3i> cache;

	private final Dimension dim;

	private final long lastTime;

	private final int radius;

	private static final Logger log = LoggerFactory.getLogger(WorldUpdateProcessor.class);

	public WorldUpdateProcessor(Map<String, Vector3i> cache, Dimension dim, long lastTime, int radius) {
		this.cache = cache;
		this.dim = dim;
		this.lastTime = lastTime;
		this.radius = radius;
	}

	@Override
	public Queue<WorldUpdate> call() {
		Queue<WorldUpdate> messages = new LinkedList<>();

		for (User user : UserStore.getUsers()) {

			int x = PositionUtils.getChunkFromGlobal((int) user.getPosition().getX(), Chunk.CHUNK_SIZE);
			int y = PositionUtils.getChunkFromGlobal((int) user.getPosition().getY(), Chunk.CHUNK_SIZE);
			int z = PositionUtils.getChunkFromGlobal((int) user.getPosition().getZ(), Chunk.CHUNK_SIZE);

			Vector3i chunkPos = new Vector3i(x, y, z);

			boolean isNew = false;

			if (!cache.containsKey(user.getName())) {
				cache.put(user.getName(), chunkPos);
				isNew = true;
			}

			Vector3i oldPos = chunkPos;

			if (!cache.get(user.getName()).equals(chunkPos)) {
				oldPos = cache.get(user.getName());
				cache.put(user.getName(), chunkPos);
			}

			List<Vector3i> oldChunks = new ArrayList<>();
			List<Vector3i> newChunks = new ArrayList<>();
			List<Vector3i> sameChunks = new ArrayList<>();

			for (int ix = -radius; ix <= radius; ix++) {
				for (int iy = -radius; iy <= radius; iy++) {
					for (int iz = -radius; iz <= radius; iz++) {
						if (!isNew) {
							oldChunks.add(oldPos.add(ix, iy, iz));
						}
						newChunks.add(chunkPos.add(ix, iy, iz));
					}
				}
			}

			for (Vector3i pos : oldChunks) {
				if (newChunks.contains(pos)) {
					sameChunks.add(pos);
				}
			}

			oldChunks.removeAll(sameChunks);
			newChunks.removeAll(sameChunks);

			for (Vector3i pos : oldChunks) {
				Vector3i start = pos.multiply(Chunk.CHUNK_SIZE);
				Vector3i end = pos.add(1).multiply(Chunk.CHUNK_SIZE);
				messages.add(new WorldUpdate(user.getConnection(), new ClearGroupMessage(start, end)));
			}

			for (Vector3i pos :newChunks) {
				dim.hasUpdated(pos, 0);//TODO use a new load chunk method
				updateChunk(messages, user.getConnection(), pos);
			}

			for (Vector3i pos : sameChunks) {
				if (dim.hasUpdated(pos, lastTime)) {
					updateChunk(messages, user.getConnection(), pos);
				}
			}
		}

		return messages;
	}

	private void updateChunk(Queue<WorldUpdate> messages, HostedConnection connection, Vector3i chunkPos) {
		Vector3i basePos = chunkPos.multiply(Chunk.CHUNK_SIZE);
		List<BlockUpdateMessage> updates = new ArrayList<>((int) Math.pow(Chunk.CHUNK_SIZE, 3));
		for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
			for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
				for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
					Vector3i pos = basePos.add(x, y, z);
					if (isVisible(pos)) {
						Block block = dim.getBlock(pos);
						String[] textures = block.getTextures();
						if (textures.length > 0) {
							updates.add(new BlockUpdateMessage(pos, textures));
						}
					}
				}
			}
		}

		int start = 0;
		while (start < updates.size()) {
			int end = Math.min(start + 512, updates.size());
			BlockUpdateMessage[] updateArray = new BlockUpdateMessage[end - start];
			for (int i = start; i < end; i++) {
				updateArray[i - start] = updates.get(i);
			}
			messages.add(new WorldUpdate(connection, new GroupUpdateMessage(updateArray)));
			start = end;
		}
	}

	private boolean isVisible(Vector3i pos) {

		if (dim.getBlock(pos.add(1, 0, 0)).isTransparent()) {
			return true;
		}

		if (dim.getBlock(pos.add(-1, 0, 0)).isTransparent()) {
			return true;
		}

		if (dim.getBlock(pos.add(0, 1, 0)).isTransparent()) {
			return true;
		}

		if (dim.getBlock(pos.add(0, -1, 0)).isTransparent()) {
			return true;
		}

		if (dim.getBlock(pos.add(0, 0, 1)).isTransparent()) {
			return true;
		}

		if (dim.getBlock(pos.add(0, 0, -1)).isTransparent()) {
			return true;
		}

		return false;
	}

}
