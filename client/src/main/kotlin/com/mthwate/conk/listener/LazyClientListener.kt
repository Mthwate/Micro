package com.mthwate.conk.listener

import com.jme3.network.Client
import com.jme3.network.Message

/**
 * @author mthwate
 */
open class LazyClientListener<T : Message>(private val receiver: (Client, T) -> Unit) : AbstractClientListener<T>() {

	override fun onReceived(src: Client, msg: T) {
		receiver.invoke(src, msg)
	}

}