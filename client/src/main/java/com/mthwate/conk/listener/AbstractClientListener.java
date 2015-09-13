package com.mthwate.conk.listener;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.mthwate.conk.message.AbstractMessageListener;

/**
 * @author mthwate
 */
public abstract class AbstractClientListener<T extends Message> extends AbstractMessageListener<Client, T> {}
