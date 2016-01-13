package com.mthwate.conk.listener

import com.jme3.network.HostedConnection
import com.jme3.network.Message
import com.mthwate.conk.message.AbstractMessageListener

/**
 * @author mthwate
 */
abstract class AbstractServerListener<T : Message> : AbstractMessageListener<HostedConnection, T>()
