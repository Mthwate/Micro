package com.mthwate.conk.listener

import com.jme3.network.HostedConnection
import com.jme3.network.Message

/**
 * @author mthwate
 */
open class LazyServerListener<T : Message>(private val receiver: (HostedConnection, T) -> Unit) : AbstractServerListener<T>() {

	override fun onReceived(src: HostedConnection, msg: T) {
		receiver.invoke(src, msg)
	}

}