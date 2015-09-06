package com.mthwate.conk.listener;

import com.jme3.network.HostedConnection;
import com.mthwate.conk.message.PositionMessage;
import com.mthwate.conk.user.User;
import com.mthwate.conk.user.UserStore;

/**
 * @author mthwate
 */
public class PositionListener extends AbstractServerListener<PositionMessage> {

	@Override
	protected void onReceived(HostedConnection source, PositionMessage m) {
		User user = UserStore.getUser(source);
		user.setPosition(m.getPosition());
	}

}
