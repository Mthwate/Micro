package com.mthwate.conk;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.font.BitmapText;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.scene.plugins.blender.BlenderModelLoader;
import com.mthwate.conk.action.ActionUtils;
import com.mthwate.conk.action.LeftClickAction;
import com.mthwate.conk.action.RightClickAction;
import com.mthwate.conk.info.BlockInfo;
import com.mthwate.conk.info.TextureInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

		//flyCam.setDragToRotate(true);
		//inputManager.setCursorVisible(true);

		assetManager.registerLocator("assets/textures", ClasspathLocator.class);
		assetManager.registerLocator("assets/models", ClasspathLocator.class);
		assetManager.registerLoader(BlenderModelLoader.class, "blend");






		world = new World();

		BlockInfo block = new BlockInfo(new TextureInfo("ceramicTile"));

		for (int x = -16; x < 32; x++) {
			for (int z = -16; z < 32; z++) {
				world.setBlock(x, 0, z, block);
			}
		}

		world.setBlock(6, 3, 6, new BlockInfo(new TextureInfo("Stone"), new Vector3f(0, 0, 1)));
		world.setBlock(10, 3, 10, new BlockInfo(new TextureInfo("Stone"), new Vector3f(0, 1, 0)));
		world.setBlock(6, 3, 10, new BlockInfo(new TextureInfo("Stone"), new Vector3f(1, 0, 0)));
		//world.setBlock(10, 3, 6, new BlockInfo(new TextureInfo("Stone"), new Vector3f(1, 1, 1)));


		world.setBlock(10, 2, 10, block);

		world.setBlock(11, 2, 11, block);
		world.setBlock(11, 2, 9, block);
		world.setBlock(9, 2, 11, block);
		world.setBlock(9, 2, 9, block);

		world.setBlock(10, 2, 11, block);
		world.setBlock(10, 2, 9, block);
		world.setBlock(11, 2, 10, block);
		world.setBlock(9, 2, 10, block);


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




		cam.setLocation(new Vector3f(0, 2, -15));
		cam.lookAtDirection(new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));
		flyCam.setMoveSpeed(10);

		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(5));
		//rootNode.addLight(al);

		ActionUtils.register(inputManager, new LeftClickAction(cam, world));
		ActionUtils.register(inputManager, new RightClickAction(cam, world));

		//viewPort.setBackgroundColor(ColorRGBA.White);


		initCrossHairs();






		/*
		cam.setLocation(new Vector3f(5, 5, 5));

		cam.lookAt(new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));




		BlenderKey key = new BlenderKey("butterfly.blend");



		Spatial model = assetManager.loadModel(key);


		AnimControl control = ((Node) model).getChild("Armature").getControl(AnimControl.class);
		AnimChannel channel = control.createChannel();
		System.out.println(control.getAnimationNames());
		channel.setAnim("flapWings");


		model.setMaterial(AssetStore.getMaterial(assetManager, new BlockInfo(new TextureInfo("dirt")), Side.TOP, ColorRGBA.White));


		rootNode.attachChild(model);
		*/



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
	}

	@Override
	public void destroy() {

		if (client != null && client.isConnected()) {
			client.close();
		}

		super.destroy();
	}

}
