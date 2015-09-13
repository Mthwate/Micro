package com.mthwate.conk.listener;

import com.jme3.bullet.BulletAppState;
import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import com.mthwate.conk.message.BlockUpdateMessage;
import com.mthwate.conk.message.LoginMessage;
import com.mthwate.conk.user.UserStore;
import com.mthwate.conk.world.Dimension;
import com.mthwate.conk.world.generator.RandomWorldGenerator;
import com.mthwate.datlib.math.set.Set3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

		Dimension dim = new Dimension("world", new RandomWorldGenerator());
		for (int x = -10; x <= 10; x++) {
			for (int y = -25; y <= 10; y++) {
				for (int z = -10; z <= 10; z++) {
					String name = dim.getBlock(x, y, z).getName();
					if (!name.equals("air")) {
						source.send(new BlockUpdateMessage(new Set3i(x, y, z), name));
					}
				}
			}
		}
	}

}
