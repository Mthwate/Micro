package com.mthwate.conk.message

import com.jme3.network.AbstractMessage
import com.jme3.network.serializing.Serializable

/**
 * @author mthwate
 */
@Serializable
class PlayerPropertiesMessage(var radius: Float = 0f, var height: Float = 0f, var mass: Float = 0f) : AbstractMessage()
