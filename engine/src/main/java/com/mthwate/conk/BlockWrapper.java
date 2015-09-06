package com.mthwate.conk;

import com.mthwate.conk.info.BlockInfo;

/**
 * @author mthwate
 */
public class BlockWrapper {

	private final boolean[] enabled = new boolean[6];

	private final BlockInfo block;

	public BlockWrapper(BlockInfo block) {
		for (int i = 0; i < 6; i++) {
			enabled[i] = true;
		}
		this.block = block;
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
