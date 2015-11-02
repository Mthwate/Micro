package com.mthwate.conk.message;

import com.jme3.network.serializing.Serializable;
import com.mthwate.datlib.math.vector.Vector3i;

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

	public BlockUpdateMessage(Vector3i pos, String texture) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.texture = texture;
	}

	public Vector3i getPosition() {
		return new Vector3i(x, y, z);
	}

	public String getTexture() {
		return texture;
	}

}
