package com.mthwate.conk.message

import com.jme3.network.AbstractMessage
import com.jme3.network.serializing.Serializable

/**
 * @author mthwate
 */
@Serializable
class MoveMessage(var x: Float = 0f, var z: Float = 0f) : AbstractMessage()
