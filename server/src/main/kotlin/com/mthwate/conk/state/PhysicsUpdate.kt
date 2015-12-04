package com.mthwate.conk.state

import com.jme3.bullet.control.RigidBodyControl
import com.jme3.scene.Node
import com.mthwate.datlib.math.vector.Vector3i

/**
 * @author mthwate
 */
class PhysicsUpdate(val pos: Vector3i, val node: Node, val control: RigidBodyControl)
