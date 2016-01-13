package com.mthwate.conk.listener

import com.jme3.network.Client
import com.mthwate.conk.message.PlayerPropertiesMessage
import com.mthwate.conk.state.MovementAppState

/**
 * @author mthwate
 */
class PlayerPropertiesListener(private val movementAppState: MovementAppState) : AbstractClientListener<PlayerPropertiesMessage>() {

	override fun onReceived(src: Client, msg: PlayerPropertiesMessage) {
		movementAppState.initControl(msg.radius, msg.height, msg.mass)
	}

}
