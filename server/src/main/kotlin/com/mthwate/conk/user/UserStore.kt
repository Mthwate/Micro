package com.mthwate.conk.user

import com.jme3.bullet.BulletAppState
import com.jme3.math.Vector3f
import com.jme3.network.HostedConnection
import com.jme3.scene.Node
import com.mthwate.conk.world.SaveUtils
import org.slf4j.LoggerFactory
import java.util.ArrayList
import java.util.HashMap

/**
 * @author mthwate
 */
object UserStore {

	private val log = LoggerFactory.getLogger(UserStore::class.java)

	private val users = HashMap<String, User>()

	fun getUser(username: String) = users[username]

	fun getUser(connection: HostedConnection) = getUser(getUsername(connection))

	fun getUsers() = ArrayList(users.values)

	private fun getUsername(connection: HostedConnection) = connection.getAttribute<String>("username")

	fun loginUser(connection: HostedConnection, username: String, physicsNode: Node, bulletAppState: BulletAppState) {
		connection.setAttribute("username", username)
		if (!users.containsKey(username)) {
			var user: User? = SaveUtils.loadUser(username, connection)

			if (user == null) {
				log.info("Creating new user for {}", username)
				user = User(username, connection)
				user.position = Vector3f(0f, 0f, 0f)
			}

			users.put(username, user)

			physicsNode.attachChild(user.spatial)

			bulletAppState.physicsSpace.add(user.control)
		}
	}

	fun logoutUser(connection: HostedConnection, bulletAppState: BulletAppState) {
		val username = getUsername(connection)
		val user = users[username]
		if (user != null) {
			user.spatial.removeFromParent()
			for (i in 0..user.spatial.numControls - 1) {
				val control = user.spatial.getControl(i)
				user.spatial.removeControl(control)
				bulletAppState.physicsSpace.remove(control)
			}
			users.remove(username)
			SaveUtils.saveUser(user)
		}
	}

}
