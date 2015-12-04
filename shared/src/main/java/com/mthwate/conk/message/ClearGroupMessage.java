package com.mthwate.conk.message;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.mthwate.datlib.math.vector.Vector3i;

/**
 * @author mthwate
 */
@Serializable
public class ClearGroupMessage extends AbstractMessage {

	private int sx;
	private int sy;
	private int sz;

	private int ex;
	private int ey;
	private int ez;

	public ClearGroupMessage() {}

	public ClearGroupMessage(Vector3i start, Vector3i end) {
		this.sx = start.getX();
		this.sy = start.getY();
		this.sz = start.getZ();

		this.ex = end.getX();
		this.ey = end.getY();
		this.ez = end.getZ();
	}

	public Vector3i getStart() {
		return new Vector3i(sx, sy, sz);
	}

	public Vector3i getEnd() {
		return new Vector3i(ex, ey, ez);
	}

}
