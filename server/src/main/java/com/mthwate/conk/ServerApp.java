package com.mthwate.conk;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.kernel.KernelException;
import com.mthwate.conk.listener.BlockBreakListener;
import com.mthwate.conk.listener.JumpListener;
import com.mthwate.conk.listener.LoginListener;
import com.mthwate.conk.listener.LogoutListener;
import com.mthwate.conk.listener.MoveListener;
import com.mthwate.conk.message.BlockBreakMessage;
import com.mthwate.conk.message.JumpMessage;
import com.mthwate.conk.message.LoginMessage;
import com.mthwate.conk.message.MessageUtils;
import com.mthwate.conk.message.MoveMessage;
import com.mthwate.conk.state.PhysicsAppState;
import com.mthwate.conk.state.PositionAppState;
import com.mthwate.conk.state.WorldUpdateAppState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author mthwate
 */
public class ServerApp extends SimpleApplication {

	private static final Logger log = LoggerFactory.getLogger(ServerApp.class);

	@Deprecated
	private static final int PORT = 6969;

	private Server server;

	private ConsoleThread consoleThread;

	@Override
	public void simpleInitApp() {
		try {
			server = Network.createServer(PORT);
		} catch (IOException e) {
			log.error("Failed to create server on port {}", PORT);
		}

		if (server != null) {
			try {
				server.start();
			} catch (KernelException e) {
				log.error("Failed to start server on port {}", PORT);
			}
		}

		if (server != null && server.isRunning()) {

			MessageUtils.register();



			BulletAppState bulletAppState = new BulletAppState();
			stateManager.attach(bulletAppState);




			server.addConnectionListener(new LogoutListener(bulletAppState));
			server.addMessageListener(new LoginListener(rootNode, bulletAppState), LoginMessage.class);
			server.addMessageListener(new JumpListener(), JumpMessage.class);
			server.addMessageListener(new MoveListener(), MoveMessage.class);
			server.addMessageListener(new BlockBreakListener(), BlockBreakMessage.class);

			consoleThread = new ConsoleThread(this);
			consoleThread.start();








			stateManager.attach(new PhysicsAppState(bulletAppState, rootNode));
			stateManager.attach(new PositionAppState());
			stateManager.attach(new WorldUpdateAppState());









		} else {
			this.stop();
		}




	}

	@Override
	public void simpleUpdate(float tpf) {
		/*
		for (HostedConnection connection : server.getConnections()) {
			User user = UserStore.getUser(connection);
			if (user != null) {
				System.out.println(user.getName() + ": " + user.getPosition());
			}
		}
		*/
	}

	@Override
	public void destroy() {

		if (consoleThread != null) {
			consoleThread.shutdown();
		}

		if (server != null && server.isRunning()) {
			server.close();
		}

		super.destroy();
	}

}
