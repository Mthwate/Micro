package com.mthwate.conk;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapText;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.kernel.KernelException;
import com.jme3.scene.Node;
import com.jme3.scene.plugins.blender.BlenderModelLoader;
import com.mthwate.conk.action.ActionUtils;
import com.mthwate.conk.action.JumpAction;
import com.mthwate.conk.action.LeftClickAction;
import com.mthwate.conk.action.MoveBackwardAction;
import com.mthwate.conk.action.MoveForwardAction;
import com.mthwate.conk.action.MoveLeftAction;
import com.mthwate.conk.action.MoveRightAction;
import com.mthwate.conk.listener.BlockUpdateListener;
import com.mthwate.conk.listener.ChunkUpdateListener;
import com.mthwate.conk.listener.ClearGroupListener;
import com.mthwate.conk.listener.PlayerPositionListener;
import com.mthwate.conk.listener.PlayerPropertiesListener;
import com.mthwate.conk.message.BlockUpdateMessage;
import com.mthwate.conk.message.ChunkUpdateMessage;
import com.mthwate.conk.message.ClearGroupMessage;
import com.mthwate.conk.message.LoginMessage;
import com.mthwate.conk.message.MessageUtils;
import com.mthwate.conk.message.PlayerPositionMessage;
import com.mthwate.conk.message.PlayerPropertiesMessage;
import com.mthwate.conk.state.MovementAppState;
import com.mthwate.datlib.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author mthwate
 */
public class ClientApp extends SimpleApplication {

	private static final Logger log = LoggerFactory.getLogger(ClientApp.class);

	@Deprecated
	private static final int PORT = 6969;

	@Deprecated
	private static final String IP = PropUtils.getIp();

	private Client client;

	@Override
	public void simpleInitApp() {
		if (connect()) {

			assetManager.registerLocator("assets/textures/Nature/Surface/Dirt", ClasspathLocator.class);
			assetManager.registerLocator("assets/textures/Nature/Underground/Stone/Regular", ClasspathLocator.class);
			assetManager.registerLocator("assets/textures/Nature/Underground/Stone/Cavern", ClasspathLocator.class);




			assetManager.registerLocator("assets/models", ClasspathLocator.class);
			assetManager.registerLocator("assets/sounds", ClasspathLocator.class);
			assetManager.registerLoader(BlenderModelLoader.class, "blend");


			cam.setFrustumPerspective(45f, (float)cam.getWidth() / cam.getHeight(), 0.001f, 1000f);

			ActionUtils.register(inputManager, new LeftClickAction(cam, WorldStore.getWorld(), client));
			//ActionUtils.register(inputManager, new RightClickAction(cam, world));
			ActionUtils.register(inputManager, new JumpAction(client));
			ActionUtils.register(inputManager, new MoveForwardAction());
			ActionUtils.register(inputManager, new MoveBackwardAction());
			ActionUtils.register(inputManager, new MoveRightAction());
			ActionUtils.register(inputManager, new MoveLeftAction());




			BulletAppState bulletAppState = new BulletAppState();
			stateManager.attach(bulletAppState);

			WorldStore.init(stateManager, rootNode);
			WorldStore.setWorld(new World(bulletAppState));


			Node camNode = new Node();

			MovementAppState movementAppState = new MovementAppState(client, bulletAppState, camNode);

			stateManager.attach(movementAppState);


			client.addMessageListener(new BlockUpdateListener(), BlockUpdateMessage.class);
			client.addMessageListener(new ChunkUpdateListener(), ChunkUpdateMessage.class);
			client.addMessageListener(new ClearGroupListener(), ClearGroupMessage.class);
			client.addMessageListener(new PlayerPositionListener(camNode, guiNode, guiFont), PlayerPositionMessage.class);
			client.addMessageListener(new PlayerPropertiesListener(movementAppState), PlayerPropertiesMessage.class);


			initCrossHairs();

			MessageUtils.register();

			client.send(new LoginMessage(RandomStringUtils.randomAlphabetic(8)));

			flyCam.setZoomSpeed(0);
			flyCam.setMoveSpeed(0);

		} else {
			this.stop();
		}
	}

	private boolean connect() {
		try {
			client = Network.connectToServer(IP, PORT);
		} catch (IOException e) {
			log.error("Failed to connect to server on port {}", PORT);
		}


		if (client != null) {
			try {
				client.start();
			} catch (KernelException e) {
				log.error("Failed to start client");
			}
		}

		return client != null;
	}

	private void initCrossHairs() {
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		BitmapText ch = new BitmapText(guiFont, false);
		ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
		ch.setText("+");
		ch.setLocalTranslation(settings.getWidth() / 2 - ch.getLineWidth()/2, settings.getHeight() / 2 + ch.getLineHeight()/2, 0);
		guiNode.attachChild(ch);
	}

	@Override
	public void simpleUpdate(float tpf) {
		if (tpf * 30 > 1) {
			log.info("Tick took too long ({} sec)", tpf);
		}
	}

	@Override
	public void destroy() {

		IOUtils.close(WorldStore.getWorld());

		if (client != null && client.isConnected()) {
			client.close();
		}

		super.destroy();
	}

}
