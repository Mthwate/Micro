package com.mthwate.conk.listener;

import com.jme3.network.Client;
import com.mthwate.conk.WorldStore;
import com.mthwate.conk.info.BlockInfo;
import com.mthwate.conk.info.TextureInfo;
import com.mthwate.conk.message.BlockUpdateMessage;
import com.mthwate.conk.message.GroupUpdateMessage;
import com.mthwate.datlib.math.vector.Vector3i;

/**
 * @author mthwate
 */
public class GroupUpdateListener extends AbstractClientListener<GroupUpdateMessage> {

	@Override
	protected void onReceived(Client source, GroupUpdateMessage m) {
		for (BlockUpdateMessage update : m.getUpdates()) {
			Vector3i pos = update.getPosition();
			if (update.getTexture() != null) {
				WorldStore.getWorld().setBlock(pos.getX(), pos.getY(), pos.getZ(), new BlockInfo(new TextureInfo(update.getTexture())));
			} else {
				WorldStore.getWorld().setBlock(pos.getX(), pos.getY(), pos.getZ(), null);
			}
		}
	}

}
