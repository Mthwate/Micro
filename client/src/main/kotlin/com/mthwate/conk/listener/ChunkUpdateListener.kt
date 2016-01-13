package com.mthwate.conk.listener

import com.jme3.network.Client
import com.mthwate.conk.UpdateUtils
import com.mthwate.conk.message.ChunkUpdateMessage

/**
 * @author mthwate
 */
class ChunkUpdateListener : AbstractClientListener<ChunkUpdateMessage>() {

	override fun onReceived(src: Client, msg: ChunkUpdateMessage) {
		val texturesMap = msg.textures

		for (x in 0..msg.size.x - 1) {
			for (y in 0..msg.size.y - 1) {
				for (z in 0..msg.size.z - 1) {
					val textures = texturesMap[x][y][z]
					UpdateUtils.setBlock(textures, msg.start.add(x, y, z))
				}
			}
		}
	}

}
