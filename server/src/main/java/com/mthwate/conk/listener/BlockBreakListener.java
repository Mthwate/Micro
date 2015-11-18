package com.mthwate.conk.listener;

import com.jme3.network.HostedConnection;
import com.mthwate.conk.block.BlockAir;
import com.mthwate.conk.message.BlockBreakMessage;
import com.mthwate.conk.world.DimensionStore;

/**
 * @author mthwate
 */
public class BlockBreakListener extends AbstractServerListener<BlockBreakMessage> {

	@Override
	protected void onReceived(HostedConnection source, BlockBreakMessage m) {
		DimensionStore.getDimension().setBlock(new BlockAir(), m.getPos());

	}

}
