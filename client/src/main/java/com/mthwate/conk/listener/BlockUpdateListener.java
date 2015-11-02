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
		if (m.getTexture() != null) {
			WorldStore.getWorld().setBlock(pos.getX(), pos.getY(), pos.getZ(), new BlockInfo(new TextureInfo(m.getTexture())));
		} else {
			WorldStore.getWorld().setBlock(pos.getX(), pos.getY(), pos.getZ(), null);
		}
	}

}
