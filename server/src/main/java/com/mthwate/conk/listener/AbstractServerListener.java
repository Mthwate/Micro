package com.mthwate.conk.listener;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.mthwate.conk.message.AbstractMessageListener;

/**
 * @author mthwate
 */
public abstract class AbstractServerListener<T extends Message> extends AbstractMessageListener<HostedConnection, T> {

}
