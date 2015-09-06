package com.mthwate.conk.listener;

import com.jme3.network.HostedConnection;
import com.mthwate.conk.message.LoginMessage;
import com.mthwate.conk.user.UserStore;

/**
 * @author mthwate
 */
public class LoginListener extends AbstractServerListener<LoginMessage> {

	@Override
	protected void onReceived(HostedConnection source, LoginMessage m) {
		String username = m.getUsername();
		UserStore.loginUser(source, username);
	}

}
