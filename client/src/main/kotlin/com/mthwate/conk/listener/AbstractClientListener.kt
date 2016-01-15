package com.mthwate.conk.listener

import com.jme3.network.Client
import com.jme3.network.Message
import com.mthwate.conk.message.AbstractMessageListener

/**
 * @author mthwate
 */
abstract class AbstractClientListener<T : Message> : AbstractMessageListener<Client, T>()
