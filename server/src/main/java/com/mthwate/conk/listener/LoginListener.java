package com.mthwate.conk.listener;

import com.jme3.bullet.BulletAppState;
import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import com.mthwate.conk.PropUtils;
import com.mthwate.conk.message.LoginMessage;
import com.mthwate.conk.message.PlayerPropertiesMessage;
import com.mthwate.conk.user.UserStore;

/**
 * @author mthwate
 */
public class LoginListener extends AbstractServerListener<LoginMessage> {

	private final Node physicsNode;

	private final BulletAppState bulletAppState;

	public LoginListener(Node physicsNode, BulletAppState bulletAppState) {
		this.physicsNode = physicsNode;
		this.bulletAppState = bulletAppState;
	}

	@Override
	protected void onReceived(HostedConnection source, LoginMessage m) {
		String username = m.getUsername();
		UserStore.loginUser(source, username, physicsNode, bulletAppState);
		source.send(new PlayerPropertiesMessage(PropUtils.getPlayerRadius(), PropUtils.getPlayerHeight(), PropUtils.getPlayerMass()));
	}

}
