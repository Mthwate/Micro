package com.mthwate.conk.listener;

import com.jme3.network.Client;
import com.mthwate.conk.UpdateUtils;
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
		UpdateUtils.setBlock(textures, pos);
	}

}
