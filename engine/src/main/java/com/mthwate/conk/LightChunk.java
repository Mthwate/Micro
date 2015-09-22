package com.mthwate.conk;

import com.jme3.math.Vector3f;

/**
 * @author mthwate
 */
public class LightChunk {

	private final Vector3f[][][] chunk;

	public LightChunk(int size) {
		this.chunk = new Vector3f[size][size][size];
	}

	public void setLight(int x, int y, int z, Vector3f val) {
		chunk[x][y][z] = val;
	}

	public Vector3f getLight(int x, int y, int z) {
		Vector3f tmpLight = chunk[x][y][z];
		if (tmpLight == null) {
			tmpLight = new Vector3f();
			chunk[x][y][z] = tmpLight;
		}
		return tmpLight;
	}

}
