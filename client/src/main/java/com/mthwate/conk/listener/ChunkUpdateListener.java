package com.mthwate.conk.listener;

import com.jme3.network.Client;
import com.mthwate.conk.UpdateUtils;
import com.mthwate.conk.message.ChunkUpdateMessage;

/**
 * @author mthwate
 */
public class ChunkUpdateListener extends AbstractClientListener<ChunkUpdateMessage> {

	@Override
	protected void onReceived(Client source, ChunkUpdateMessage m) {
		String[][][][] texturesMap = m.getTextures();

		for (int x = 0; x < m.getSize().getX(); x++) {
			for (int y = 0; y < m.getSize().getY(); y++) {
				for (int z = 0; z < m.getSize().getZ(); z++) {
					String[] textures = texturesMap[x][y][z];
					UpdateUtils.setBlock(textures, m.getStart().add(x, y, z));
				}
			}
		}
	}

}
