package com.mthwate.conk.message;

import com.jme3.network.Message;
import com.jme3.network.MessageListener;

/**
 * @author mthwate
 */
public abstract class AbstractMessageListener<S, T extends Message> implements MessageListener<S> {

	protected abstract void onReceived(S source, T m);

	@Override
	public void messageReceived(S source, Message m) {
		T message = (T) m;//TODO gracefully handle this
		onReceived(source, message);
	}

}
