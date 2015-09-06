package com.mthwate.conk.user;

import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.mthwate.conk.world.SaveUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class UserStore {

	private static Map<String, User> users = new HashMap<>();

	public static User getUser(HostedConnection connection) {
		return users.get(getUsername(connection));
	}

	public static void loginUser(HostedConnection connection, String username) {
		connection.setAttribute("username", username);
		if (!users.containsKey(username)) {
			User user = SaveUtils.loadUser(username);

			if (user == null) {
				user = new User(username);
				user.setPosition(new Vector3f(0f, 0f, 0f));
				users.put(username, user);
			}
		}
	}

	public static void logoutUser(HostedConnection connection) {
		String username = getUsername(connection);
		User user = users.get(username);
		if (user != null) {
			users.remove(username);
			SaveUtils.saveUser(user);
		}
	}

	private static String getUsername(HostedConnection connection) {
		return connection.getAttribute("username");
	}

}
