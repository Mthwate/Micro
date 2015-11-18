package com.mthwate.conk.block;

import com.mthwate.conk.command.Exec;

/**
 * @author mthwate
 */
@Exec
public class BlockDirt extends BlockBase {

	@Override
	public String getName() {
		return "dirt";
	}

	@Override
	public String[] getTextures() {
		return new String[] {getName(), getName() + "Side"};
	}

}
