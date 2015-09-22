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
import com.mthwate.datlib.math.set.Set3i;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class WorldUpdateAppState extends AbstractAppState {

	private final Dimension dim = new Dimension("world", new RandomWorldGenerator());//TODO remove this

	private static final int RADIUS = 1;//TODO remove this

	private final Map<String, Set3i> cache = new HashMap<>();

	@Override
	public void update(float tpf) {

		for (User user : UserStore.getUsers()) {
			if (!cache.containsKey(user.getName())) {

				int x = PositionUtils.getChunkFromGlobal((int) user.getPosition().getX(), Chunk.CHUNK_SIZE);
				int y = PositionUtils.getChunkFromGlobal((int) user.getPosition().getY(), Chunk.CHUNK_SIZE);
				int z = PositionUtils.getChunkFromGlobal((int) user.getPosition().getZ(), Chunk.CHUNK_SIZE);

				cache.put(user.getName(), new Set3i(x, y, z));

				for (int ix = -RADIUS; ix <= RADIUS; ix++) {
					for (int iy = -RADIUS; iy <= RADIUS; iy++) {
						for (int iz = -RADIUS; iz <= RADIUS; iz++) {
							updateChunk(user.getConnection(), x + ix, y + iy, z + iz);
						}
					}
				}
			}
		}

	}

	private void updateChunk(HostedConnection connection, int cx, int cy, int cz) {
		for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
			for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
				for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
					Set3i pos = new Set3i(cx * Chunk.CHUNK_SIZE + x, cy * Chunk.CHUNK_SIZE + y, cz * Chunk.CHUNK_SIZE + z);
					Block block = dim.getBlock(pos);
					if (!block.getName().equals("air")) {
						connection.send(new BlockUpdateMessage(pos, block.getName()));
					}
				}
			}
		}

	}

}
