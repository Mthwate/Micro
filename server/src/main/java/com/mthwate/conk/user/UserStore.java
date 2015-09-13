package com.mthwate.conk.user;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import com.jme3.scene.control.Control;
import com.mthwate.conk.PropUtils;
import com.mthwate.conk.world.SaveUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mthwate
 */
public class UserStore {

	private static Map<String, User> users = new HashMap<>();

	public static User getUser(HostedConnection connection) {
		return users.get(getUsername(connection));
	}

	public static List<User> getUsers() {
		List<User> users = new ArrayList<>();
		for (User user : UserStore.users.values()) {
			users.add(user);
		}
		return users;
	}

	public static void loginUser(HostedConnection connection, String username, Node physicsNode, BulletAppState bulletAppState) {
		connection.setAttribute("username", username);
		if (!users.containsKey(username)) {
			User user = SaveUtils.loadUser(username, connection);

			if (user == null) {
				user = new User(username, connection);
				user.setPosition(new Vector3f(0f, 0f, 0f));
			}

			users.put(username, user);

			physicsNode.attachChild(user.getSpatial());

			BetterCharacterControl control = new BetterCharacterControl(PropUtils.getPlayerRadius(), PropUtils.getPlayerHeight(), PropUtils.getPlayerMass());

			user.getSpatial().addControl(control);
			bulletAppState.getPhysicsSpace().add(control);
		}
	}

	public static void logoutUser(HostedConnection connection, BulletAppState bulletAppState) {
		String username = getUsername(connection);
		User user = users.get(username);
		if (user != null) {
			user.getSpatial().removeFromParent();
			for (int i = 0; i < user.getSpatial().getNumControls(); i++) {
				Control control = user.getSpatial().getControl(i);
				user.getSpatial().removeControl(control);
				bulletAppState.getPhysicsSpace().remove(control);
			}
			users.remove(username);
			SaveUtils.saveUser(user);
		}
	}

	private static String getUsername(HostedConnection connection) {
		return connection.getAttribute("username");
	}

}
