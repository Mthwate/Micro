package com.mthwate.conk.listener

import com.jme3.bullet.BulletAppState
import com.jme3.network.HostedConnection
import com.jme3.scene.Node
import com.mthwate.conk.PropUtils
import com.mthwate.conk.message.LoginMessage
import com.mthwate.conk.message.PlayerPropertiesMessage
import com.mthwate.conk.user.UserStore

/**
 * @author mthwate
 */
class LoginListener(private val physicsNode: Node, private val bulletAppState: BulletAppState) : AbstractServerListener<LoginMessage>() {

	override fun onReceived(src: HostedConnection, msg: LoginMessage) {
		val username = msg.username
		UserStore.loginUser(src, username, physicsNode, bulletAppState)
		src.send(PlayerPropertiesMessage(PropUtils.getPlayerRadius(), PropUtils.getPlayerHeight(), PropUtils.getPlayerMass()))
	}

}
