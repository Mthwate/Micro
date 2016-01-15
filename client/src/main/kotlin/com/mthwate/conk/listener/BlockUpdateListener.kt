package com.mthwate.conk.listener

import com.jme3.network.Client
import com.mthwate.conk.UpdateUtils
import com.mthwate.conk.message.BlockUpdateMessage

/**
 * @author mthwate
 */
class BlockUpdateListener : AbstractClientListener<BlockUpdateMessage>() {

	override fun onReceived(src: Client, msg: BlockUpdateMessage) {
		UpdateUtils.setBlock(msg.textures, msg.position)
	}

}
