package com.mthwate.conk.listener

import com.jme3.network.Client
import com.mthwate.conk.WorldStore
import com.mthwate.conk.message.ClearGroupMessage

/**
 * @author mthwate
 */
class ClearGroupListener : AbstractClientListener<ClearGroupMessage>() {

	override fun onReceived(src: Client, msg: ClearGroupMessage) {

		val start = msg.start
		val end = msg.end

		for (x in start.x..end.x - 1) {
			for (y in start.y..end.y - 1) {
				for (z in start.z..end.z - 1) {
					WorldStore.getWorld().setBlock(x, y, z, null)
				}
			}
		}

	}

}
