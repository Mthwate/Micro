package com.mthwate.conk;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.mthwate.conk.info.BlockInfo;

/**
 * @author mthwate
 */
public class BlockWrapper {

	private final boolean[] enabled = new boolean[6];

	private final BlockInfo block;

	private final Vector3f light = new Vector3f(0, 0, 0);

	public BlockWrapper(BlockInfo block) {
		for (int i = 0; i < 6; i++) {
			enabled[i] = true;
		}
		this.block = block;
		if (block != null && block.getLight() != null) {
			light.add(block.getLight());
		}
	}

	public void addLight(Vector3f light) {
		this.light.addLocal(Math.max(light.getX(), 0), Math.max(light.getY(), 0), Math.max(light.getZ(), 0));
	}

	public ColorRGBA getLight() {
		return new ColorRGBA(light.getX(), light.getY(), light.getZ(), 1);
	}

	public void disableSide(Side side) {
		enabled[side.getI()] = false;
	}

	public boolean isEnabled(Side side) {
		return enabled[side.getI()];
	}

	public boolean isEnabled(int side) {
		return enabled[side];
	}

	public BlockInfo getInfo() {
		return block;
	}
}
