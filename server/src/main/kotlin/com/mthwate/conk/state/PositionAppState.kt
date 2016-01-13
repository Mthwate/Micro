package com.mthwate.conk.state

import com.jme3.app.state.AbstractAppState
import com.jme3.math.Vector3f
import com.mthwate.conk.message.PlayerPositionMessage
import com.mthwate.conk.user.UserStore
import java.util.HashMap

/**
 * @author mthwate
 */
class PositionAppState : AbstractAppState() {

	private val cache = HashMap<String, Vector3f>()

	override fun update(tpf: Float) {
		UserStore.getUsers().forEach {
			val oldPos = cache[it.name]
			val newPos = it.position.clone()
			if (oldPos == null || newPos.distance(oldPos) > 0.000001) {
				val message = PlayerPositionMessage(newPos)
				it.connection.send(message)
				cache.put(it.name, newPos)
			}
		}
	}

}
