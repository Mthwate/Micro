package com.mthwate.conk.listener;

import com.jme3.network.Client;
import com.mthwate.conk.message.BlockUpdateMessage;
import com.mthwate.conk.message.GroupUpdateMessage;

/**
 * @author mthwate
 */
public class GroupUpdateListener extends AbstractClientListener<GroupUpdateMessage> {

	private final BlockUpdateListener blockUpdateListener = new BlockUpdateListener();

	@Override
	protected void onReceived(Client source, GroupUpdateMessage m) {
		for (BlockUpdateMessage update : m.getUpdates()) {
			blockUpdateListener.onReceived(source, update);
		}
	}

}
