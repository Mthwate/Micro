package com.mthwate.conk.listener;

import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import com.mthwate.conk.user.UserStore;

/**
 * @author mthwate
 */
public class LogoutListener implements ConnectionListener {

	@Override
	public void connectionAdded(Server server, HostedConnection conn) {}

	@Override
	public void connectionRemoved(Server server, HostedConnection conn) {
		UserStore.logoutUser(conn);
	}

}
