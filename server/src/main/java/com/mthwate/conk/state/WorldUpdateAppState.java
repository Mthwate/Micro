package com.mthwate.conk.state;

import com.jme3.app.state.AbstractAppState;
import com.jme3.network.HostedConnection;
import com.mthwate.conk.PositionUtils;
import com.mthwate.conk.block.Block;
import com.mthwate.conk.message.BlockUpdateMessage;
import com.mthwate.conk.user.User;
import com.mthwate.conk.user.UserStore;
import com.mthwate.conk.world.Chunk;
import com.mthwate.conk.world.Dimension;
import com.mthwate.conk.world.generator.RandomWorldGenerator;
import com.mthwate.datlib.math.vector.Vector3i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mthwate
 */
public class WorldUpdateAppState extends AbstractAppState {

	private final Dimension dim = new Dimension("world", new RandomWorldGenerator());//TODO remove this

	private static final int RADIUS = 1;//TODO remove this

	private final Map<String, Vector3i> cache = new HashMap<>();

	@Override
	public void update(float tpf) {

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
		for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
			for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
				for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
					Vector3i pos = new Vector3i(cx * Chunk.CHUNK_SIZE + x, cy * Chunk.CHUNK_SIZE + y, cz * Chunk.CHUNK_SIZE + z);
					Block block = dim.getBlock(pos);
					if (!block.getName().equals("air")) {
						connection.send(new BlockUpdateMessage(pos, block.getName()));
					}
				}
			}
		}
	}

	private void clearChunk(HostedConnection connection, int cx, int cy, int cz) {
		for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
			for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
				for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
					Vector3i pos = new Vector3i(cx * Chunk.CHUNK_SIZE + x, cy * Chunk.CHUNK_SIZE + y, cz * Chunk.CHUNK_SIZE + z);
					connection.send(new BlockUpdateMessage(pos, null));
				}
			}
		}
	}

}
