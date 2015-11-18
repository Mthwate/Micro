package com.mthwate.conk.message;

import com.jme3.network.serializing.Serializable;
import com.mthwate.datlib.math.vector.Vector3i;

/**
 * @author mthwate
 */
@Serializable
public class BlockBreakMessage extends AbstractMessage {

	private int x;
	private int y;
	private int z;

	public BlockBreakMessage() {}

	public BlockBreakMessage(Vector3i pos) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}

	public Vector3i getPos() {
		return new Vector3i(x, y, z);
	}

}
