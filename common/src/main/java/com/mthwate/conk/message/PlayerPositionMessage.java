package com.mthwate.conk.message;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;

/**
 * @author mthwate
 */
@Serializable
public class PlayerPositionMessage extends AbstractMessage {

	private Vector3f position;

	public PlayerPositionMessage() {}

	public PlayerPositionMessage(Vector3f position) {
		this.position = position;
	}

	public Vector3f getPosition() {
		return position;
	}

}
