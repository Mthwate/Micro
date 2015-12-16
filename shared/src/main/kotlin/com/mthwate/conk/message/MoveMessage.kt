package com.mthwate.conk.message

import com.jme3.network.AbstractMessage
import com.jme3.network.serializing.Serializable

/**
 * @author mthwate
 */
@Serializable
class MoveMessage(val x: Float = 0f, val z: Float = 0f) : AbstractMessage()
