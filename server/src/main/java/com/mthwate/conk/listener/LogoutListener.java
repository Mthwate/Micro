package com.mthwate.conk.listener;

import com.jme3.bullet.BulletAppState;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import com.mthwate.conk.user.UserStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mthwate
 */
public class LogoutListener implements ConnectionListener {

	private static final Logger log = LoggerFactory.getLogger(LogoutListener.class);

	private final BulletAppState bulletAppState;

	public LogoutListener(BulletAppState bulletAppState) {
		this.bulletAppState = bulletAppState;
	}

	@Override
	public void connectionAdded(Server server, HostedConnection conn) {
		log.info("Connection added");
	}

	@Override
	public void connectionRemoved(Server server, HostedConnection conn) {
		log.info("Connection removed");
		UserStore.logoutUser(conn, bulletAppState);
	}

}
