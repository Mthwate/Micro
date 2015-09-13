package com.mthwate.conk;

import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;

/**
 * @author mthwate
 */
public class WorldStore {

	private static World world;

	private static AppStateManager stateManager;

	private static Node rootNode;

	public static void init(AppStateManager stateManager, Node rootNode) {
		WorldStore.stateManager = stateManager;
		WorldStore.rootNode = rootNode;
	}

	public static void setWorld(World world) {
		if (WorldStore.world != null) {
			stateManager.detach(WorldStore.world);
			rootNode.detachChild(WorldStore.world.getNode());
		}

		WorldStore.world = world;

		stateManager.attach(WorldStore.world);
		rootNode.attachChild(WorldStore.world.getNode());
	}

	public static World getWorld() {
		return world;
	}

}
