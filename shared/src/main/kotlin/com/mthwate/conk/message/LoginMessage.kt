package com.mthwate.conk.message

import com.jme3.network.AbstractMessage
import com.jme3.network.serializing.Serializable

/**
 * @author mthwate
 */
@Serializable
class LoginMessage (val username: String = "") : AbstractMessage()