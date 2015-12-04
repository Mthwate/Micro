package com.mthwate.conk.message;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * @author mthwate
 */
@Serializable
public class GroupUpdateMessage extends AbstractMessage {

	private BlockUpdateMessage[] updates;

	public GroupUpdateMessage() {}

	public GroupUpdateMessage(BlockUpdateMessage[] updates) {
		this.updates = updates;
	}

	public BlockUpdateMessage[] getUpdates() {
		return updates;
	}

}
