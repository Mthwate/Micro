package com.mthwate.conk.user

import com.jme3.math.Vector3f
import com.jme3.network.HostedConnection
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import com.mthwate.conk.PropUtils
import com.mthwate.conk.physics.ConkCharacterControl

/**
 * @author mthwate
 */
class User(val name: String, val connection: HostedConnection) {

	val spatial: Spatial = Node()

	val control = ConkCharacterControl(PropUtils.getPlayerRadius(), PropUtils.getPlayerHeight(), PropUtils.getPlayerMass())

	init {
		spatial.addControl(control)
	}

	var position: Vector3f
		get() = spatial.localTranslation
		set(position) = control.warp(position)

}
