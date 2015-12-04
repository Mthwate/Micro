package com.mthwate.conk.message;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * @author mthwate
 */
@Serializable
public class MoveMessage extends AbstractMessage {

	private float x;

	private float z;

	public MoveMessage() {}

	public MoveMessage(float x, float z) {
		this.x = x;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

}
