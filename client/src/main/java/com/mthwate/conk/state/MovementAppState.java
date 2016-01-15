package com.mthwate.conk.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import com.mthwate.conk.message.MoveMessage;
import com.mthwate.conk.physics.ConkCharacterControl;

/**
 * @author mthwate
 */
public class MovementAppState extends AbstractAppState {

	private static boolean forward;
	private static boolean backward;
	private static boolean right;
	private static boolean left;

	private final Client client;
	private Camera cam;
	private BetterCharacterControl control;
	private final BulletAppState bulletAppState;
	private final Node camRootNode;

	private Vector2f prev = Vector2f.ZERO;

	public MovementAppState(Client client, BulletAppState bulletAppState, Node camRootNode) {
		this.client = client;
		this.bulletAppState = bulletAppState;
		this.camRootNode = camRootNode;
	}

	public void initControl(float radius, float height, float mass) {
		control = new ConkCharacterControl(radius, height, mass);
		camRootNode.addControl(control);
		bulletAppState.getPhysicsSpace().add(control);
		CameraNode camNode = new CameraNode("Camera Node", cam);
		camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
		camRootNode.attachChild(camNode);
		camNode.setLocalTranslation(new Vector3f(0, height * 0.975f, 0));
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		cam = app.getCamera();
	}

	@Override
	public void update(float tpf) {
		Vector2f direction = new Vector2f();

		Vector2f camDirection = new Vector2f(cam.getDirection().getX(), cam.getDirection().getZ());
		Vector2f camLeft = new Vector2f(cam.getLeft().getX(), cam.getLeft().getZ());

		camDirection.normalizeLocal();
		camLeft.normalizeLocal();

		camDirection.multLocal(5);
		camLeft.multLocal(4);

		if (forward) {
			direction.addLocal(camDirection.getX(), camDirection.getY());
		}

		if (backward) {
			direction.addLocal(-camDirection.getX(), -camDirection.getY());
		}

		if (right) {
			direction.addLocal(-camLeft.getX(), -camLeft.getY());
		}

		if (left) {
			direction.addLocal(camLeft.getX(), camLeft.getY());
		}

		if (control != null && !direction.equals(prev)) {
			prev = direction;
			client.send(new MoveMessage(direction.getX(), direction.getY()));
			control.setWalkDirection(new Vector3f(direction.getX(), 0, direction.getY()));
		}
	}

	public static void setForward(boolean on) {
		forward = on;
	}

	public static void setBackward(boolean on) {
		backward = on;
	}

	public static void setRight(boolean on) {
		right = on;
	}

	public static void setLeft(boolean on) {
		left = on;
	}

}
