package com.mthwate.conk.message;

import com.jme3.network.Message;
import com.jme3.network.MessageConnection;
import com.jme3.network.MessageListener;

/**
 * @author mthwate
 */
public abstract class AbstractMessageListener<S extends MessageConnection, T extends Message> implements MessageListener<S> {

	protected abstract void onReceived(S src, T msg);

	@Override
	public void messageReceived(S src, Message msg) {
		T message = (T) msg;//TODO gracefully handle this
		onReceived(src, message);
	}

}
