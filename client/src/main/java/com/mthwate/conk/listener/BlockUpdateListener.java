package com.mthwate.conk.listener;

import com.jme3.network.Client;
import com.mthwate.conk.WorldStore;
import com.mthwate.conk.info.BlockInfo;
import com.mthwate.conk.info.TextureInfo;
import com.mthwate.conk.message.BlockUpdateMessage;
import com.mthwate.datlib.math.vector.Vector3i;

/**
 * @author mthwate
 */
public class BlockUpdateListener extends AbstractClientListener<BlockUpdateMessage> {

	@Override
	protected void onReceived(Client source, BlockUpdateMessage m) {
		Vector3i pos = m.getPosition();
		String[] textures = m.getTextures();
		if (textures != null) {

			TextureInfo textureInfo = null;

			switch (m.getTextures().length) {
				case 1:
					textureInfo = new TextureInfo(textures[0]);
					break;
				case 2:
					textureInfo = new TextureInfo(textures[0], textures[1]);
					break;
				case 3:
					textureInfo = new TextureInfo(textures[0], textures[1], textures[2]);
					break;
				case 6:
					textureInfo = new TextureInfo(textures[0], textures[1], textures[2], textures[3], textures[4], textures[5]);
					break;
			}

			WorldStore.getWorld().setBlock(pos.getX(), pos.getY(), pos.getZ(), new BlockInfo(textureInfo));
		} else {
			WorldStore.getWorld().setBlock(pos.getX(), pos.getY(), pos.getZ(), null);
		}
	}

}
