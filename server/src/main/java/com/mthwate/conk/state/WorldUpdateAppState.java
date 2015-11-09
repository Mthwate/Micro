package com.mthwate.conk.state;

import com.jme3.network.HostedConnection;
import com.mthwate.conk.PositionUtils;
import com.mthwate.conk.block.Block;
import com.mthwate.conk.message.BlockUpdateMessage;
import com.mthwate.conk.message.GroupUpdateMessage;
import com.mthwate.conk.user.User;
import com.mthwate.conk.user.UserStore;
import com.mthwate.conk.world.Chunk;
import com.mthwate.conk.world.Dimension;
import com.mthwate.conk.world.DimensionStore;
import com.mthwate.datlib.math.vector.Vector3i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mthwate
 */
public class WorldUpdateAppState extends TimedAppState {

	private final Dimension dim = DimensionStore.getDimension();//TODO remove this

	private static final int RADIUS = 1;//TODO remove this

	private final Map<String, Vector3i> cache = new HashMap<>();

	@Override
	public void timedUpdate(float tpf) {

		for (User user : UserStore.getUsers()) {

			int x = PositionUtils.getChunkFromGlobal((int) user.getPosition().getX(), Chunk.CHUNK_SIZE);
			int y = PositionUtils.getChunkFromGlobal((int) user.getPosition().getY(), Chunk.CHUNK_SIZE);
			int z = PositionUtils.getChunkFromGlobal((int) user.getPosition().getZ(), Chunk.CHUNK_SIZE);

			Vector3i chunkPos = new Vector3i(x, y, z);

			if (!cache.containsKey(user.getName())) {

				cache.put(user.getName(), chunkPos);

				for (int ix = -RADIUS; ix <= RADIUS; ix++) {
					for (int iy = -RADIUS; iy <= RADIUS; iy++) {
						for (int iz = -RADIUS; iz <= RADIUS; iz++) {
							updateChunk(user.getConnection(), x + ix, y + iy, z + iz);
						}
					}
				}

			} else if (!cache.get(user.getName()).equals(chunkPos)) {
				Vector3i oldPos = cache.get(user.getName());

				cache.put(user.getName(), chunkPos);

				List<Vector3i> oldChunks = new ArrayList<>();
				List<Vector3i> newChunks = new ArrayList<>();

				for (int ix = -RADIUS; ix <= RADIUS; ix++) {
					for (int iy = -RADIUS; iy <= RADIUS; iy++) {
						for (int iz = -RADIUS; iz <= RADIUS; iz++) {
							oldChunks.add(oldPos.add(ix, iy, iz));
							newChunks.add(chunkPos.add(ix, iy, iz));
						}
					}
				}

				for (Vector3i chunk : oldChunks) {
					if (!newChunks.contains(chunk)) {
						clearChunk(user.getConnection(), chunk.getX(), chunk.getY(), chunk.getZ());
					}
				}

				for (Vector3i chunk : newChunks) {
					if (!oldChunks.contains(chunk)) {
						updateChunk(user.getConnection(), chunk.getX(), chunk.getY(), chunk.getZ());
					}
				}
			}
		}
	}

	private void updateChunk(HostedConnection connection, int cx, int cy, int cz) {
		Vector3i basePos = new Vector3i(cx * Chunk.CHUNK_SIZE, cy * Chunk.CHUNK_SIZE, cz * Chunk.CHUNK_SIZE);
		List<BlockUpdateMessage> updates = new ArrayList<>((int) Math.pow(Chunk.CHUNK_SIZE, 3));
		for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
			for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
				for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
					Vector3i pos = basePos.add(x, y, z);
					Block block = dim.getBlock(pos);
					if (!block.getName().equals("air")) {
						updates.add(new BlockUpdateMessage(pos, block.getName()));
					}
				}
			}
		}
		sendBatch(updates, connection);
	}

	private static void clearChunk(HostedConnection connection, int cx, int cy, int cz) {
		Vector3i basePos = new Vector3i(cx * Chunk.CHUNK_SIZE, cy * Chunk.CHUNK_SIZE, cz * Chunk.CHUNK_SIZE);
		List<BlockUpdateMessage> updates = new ArrayList<>((int) Math.pow(Chunk.CHUNK_SIZE, 3));
		for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
			for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
				for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
					Vector3i pos = basePos.add(x, y, z);
					updates.add(new BlockUpdateMessage(pos, null));
				}
			}
		}
		sendBatch(updates, connection);
	}

	private static void sendBatch(List<BlockUpdateMessage> updates, HostedConnection connection) {
		int start = 0;
		while (start < updates.size()) {
			int end = Math.min(start + 100, updates.size());
			BlockUpdateMessage[] updateArray = new BlockUpdateMessage[end - start];
			for (int i = start; i < end; i++) {
				updateArray[i - start] = updates.get(i);
			}
			connection.send(new GroupUpdateMessage(updateArray));
			start = end;
		}
	}

	@Override
	public long getMaxTime() {
		return 100000;
	}

}
