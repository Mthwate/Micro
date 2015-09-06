package com.mthwate.conk.message;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;

/**
 * @author mthwate
 */
@Serializable
public class PositionMessage extends AbstractMessage {

	private Vector3f position;

	public PositionMessage() {}

	public PositionMessage(Vector3f position) {
		this.position = position;
	}

	public Vector3f getPosition() {
		return position;
	}

}
