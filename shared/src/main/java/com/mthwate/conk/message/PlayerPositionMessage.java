package com.mthwate.conk.message;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * @author mthwate
 */
@Serializable
public class PlayerPositionMessage extends AbstractMessage {

	private float x;
	private float y;
	private float z;

	public PlayerPositionMessage() {}

	public PlayerPositionMessage(Vector3f position) {
		this.x = position.getX();
		this.y = position.getY();
		this.z = position.getZ();
	}

	public Vector3f getPosition() {
		return new Vector3f(x, y, z);
	}

}
