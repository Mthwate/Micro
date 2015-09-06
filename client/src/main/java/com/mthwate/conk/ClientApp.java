package com.mthwate.conk;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.BlenderKey;
import com.jme3.asset.ModelKey;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.collision.CollisionResults;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.plugins.blender.BlenderLoader;
import com.jme3.scene.plugins.blender.BlenderModelLoader;
import com.mthwate.conk.action.ActionUtils;
import com.mthwate.conk.action.LeftClickAction;
import com.mthwate.conk.info.BlockInfo;
import com.mthwate.conk.info.TextureInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author mthwate
 */
public class ClientApp extends SimpleApplication {

	private static final Logger log = LoggerFactory.getLogger(ClientApp.class);

	@Deprecated
	private static final int PORT = 6969;

	private Client client;

	private World world;

	@Override
	public void simpleInitApp() {
		/*
		try {
			client = Network.connectToServer("localhost", PORT);
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

		if (client != null) {

			MessageUtils.register();

			client.send(new LoginMessage("Mthwate"));

		} else {
			this.stop();
		}
		*/


		assetManager.registerLocator("assets/textures", ClasspathLocator.class);
		assetManager.registerLocator("assets/models", ClasspathLocator.class);
		assetManager.registerLoader(BlenderModelLoader.class, "blend");


		world = new World();

		BlockInfo block = new BlockInfo(new TextureInfo("dirt"));

		int max = 16 * (5 * 2 + 1);


		for (int x = 16; x < max; x++) {
			for (int y = 16; y < max; y++) {
				for (int z = 16; z < max; z++) {
					world.setBlock(x, y, z, block);
				}
			}
			System.out.println(x + "/" + max);
		}

		world.setBlock(1, 1, 1, block);

		//world.setBlock(0, 0, 0, new BlockInfo(new TextureInfo("dirt")));
		//world.setBlock(0, 1, 0, new BlockInfo(new TextureInfo("dirt"), "Oto"));

		//world.setBlock(0, 0, -1, new BlockInfo(new TextureInfo("Topsoil")));
		//world.setBlock(0, 0, 0, new BlockInfo(new TextureInfo("Topsoil")));
		//world.setBlock(1, 0, 0, new BlockInfo(new TextureInfo("Topsoil")));
		//world.setBlock(0, 1, 0, new BlockInfo(new TextureInfo("Topsoil")));
		//world.setBlock(0, 0, 1, new BlockInfo(new TextureInfo("Topsoil")));
		//world.setBlock(1, 1, 0, new BlockInfo(new TextureInfo("Topsoil")));
		//world.setBlock(0, 1, 1, new BlockInfo(new TextureInfo("Topsoil")));
		//world.setBlock(1, 0, 1, new BlockInfo(new TextureInfo("Topsoil")));

		stateManager.attach(world);

		rootNode.attachChild(world.getNode());


		/*
		int size = 16;

		int bigSize = 5;

		for (int cx = -bigSize; cx <= bigSize; cx++) {
			for (int cy = -bigSize; cy <= bigSize; cy++) {
				for (int cz = -bigSize; cz <= bigSize; cz++) {
					Timer timer = new Timer();
					Node node = WorldGen.genChunk(cx, cy, cz, size).genNode(assetManager);
					if (node.getChildren().size() > 0) {
						log.info("{}, {}, {}", cx, cy, cz);
						GeometryBatchFactory.optimize(node);
						node.setLocalTranslation(cx * size, cy * size, cz * size);
						rootNode.attachChild(node);
					}
					log.info("Chunk generation took {} sec", timer.getSec(5));
				}
			}
		}


		*/

		cam.setLocation(new Vector3f(0, 2, -10));
		flyCam.setMoveSpeed(10);

		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(5));
		rootNode.addLight(al);

		ActionUtils.register(inputManager, new LeftClickAction(cam, world));

		//viewPort.setBackgroundColor(ColorRGBA.White);


	}

	@Override
	public void simpleUpdate(float tpf) {
	}

	@Override
	public void destroy() {

		if (client != null && client.isConnected()) {
			client.close();
		}

		super.destroy();
	}

}
