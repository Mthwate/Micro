package com.mthwate.conk.message;

import com.jme3.network.serializing.Serializable;
import com.mthwate.datlib.math.set.Set3i;

/**
 * @author mthwate
 */
@Serializable
public class BlockUpdateMessage extends AbstractMessage {

	private int x;
	private int y;
	private int z;

	private String texture;

	public BlockUpdateMessage() {}

	public BlockUpdateMessage(Set3i pos, String texture) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.texture = texture;
	}

	public Set3i getPosition() {
		return new Set3i(x, y, z);
	}

	public String getTexture() {
		return texture;
	}

}
