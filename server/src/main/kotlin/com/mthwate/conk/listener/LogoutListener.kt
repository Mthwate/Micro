package com.mthwate.conk.listener

import com.jme3.bullet.BulletAppState
import com.jme3.network.ConnectionListener
import com.jme3.network.HostedConnection
import com.jme3.network.Server
import com.mthwate.conk.user.UserStore
import org.slf4j.LoggerFactory

/**
 * @author mthwate
 */
class LogoutListener(private val bulletAppState: BulletAppState) : ConnectionListener {

	private val log = LoggerFactory.getLogger(LogoutListener::class.java)

	override fun connectionAdded(server: Server, conn: HostedConnection) {
		log.info("Connection added")
	}

	override fun connectionRemoved(server: Server, conn: HostedConnection) {
		log.info("Connection removed")
		UserStore.logoutUser(conn, bulletAppState)
	}

}
