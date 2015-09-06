package com.mthwate.conk;

import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.kernel.KernelException;
import com.mthwate.conk.listener.LoginListener;
import com.mthwate.conk.listener.LogoutListener;
import com.mthwate.conk.listener.PositionListener;
import com.mthwate.conk.message.LoginMessage;
import com.mthwate.conk.message.MessageUtils;
import com.mthwate.conk.message.PositionMessage;
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

			server.addConnectionListener(new LogoutListener());
			server.addMessageListener(new LoginListener(), LoginMessage.class);
			server.addMessageListener(new PositionListener(), PositionMessage.class);

			consoleThread = new ConsoleThread(this);
			consoleThread.start();


			//BulletAppState bulletAppState = new BulletAppState();
			//stateManager.attach(bulletAppState);
			//BetterCharacterControl control = new BetterCharacterControl(PropUtils.getPlayerRadius(), PropUtils.getPlayerHeight(), PropUtils.getPlayerMass());




		} else {
			this.stop();
		}
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
