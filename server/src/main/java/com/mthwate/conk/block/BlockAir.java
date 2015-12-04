package com.mthwate.conk.block;

import com.mthwate.conk.command.Exec;

/**
 * @author mthwate
 */
@Exec
public class BlockAir extends BlockBase {

	@Override
	public String getName() {
		return "air";
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public boolean isTransparent() {
		return true;
	}

	@Override
	public String[] getTextures() {
		return null;
	}

}
