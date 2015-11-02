package com.mthwate.conk.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import com.jme3.network.Client;
import com.jme3.renderer.Camera;
import com.mthwate.conk.message.MoveMessage;

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

	private Vector2f prev = Vector2f.ZERO;

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

	public MovementAppState(Client client) {
		this.client = client;
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		this.cam = app.getCamera();
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

		if (!direction.equals(prev)) {
			prev = direction;
			client.send(new MoveMessage(direction.getX(), direction.getY()));
		}
	}

}
