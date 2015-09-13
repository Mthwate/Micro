package com.mthwate.conk.user;

import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * @author mthwate
 */
public class User {

	private final String name;

	private final Node node = new Node();

	private final HostedConnection connection;

	public User(String name, HostedConnection connection) {
		this.name = name;
		this.connection = connection;
	}

	public String getName() {
		return name;
	}

	public Spatial getSpatial() {
		return node;
	}

	public void setPosition(Vector3f position) {
		node.setLocalTranslation(position);
	}

	public Vector3f getPosition() {
		return node.getLocalTranslation();
	}

	public HostedConnection getConnection() {
		return connection;
	}
}
