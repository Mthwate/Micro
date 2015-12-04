package com.mthwate.conk.user;

import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.mthwate.conk.PropUtils;
import com.mthwate.conk.physics.ConkCharacterControl;

/**
 * @author mthwate
 */
public class User {

	private final String name;

	private final Spatial spatial = new Node();

	private final HostedConnection connection;

	private final ConkCharacterControl control = new ConkCharacterControl(PropUtils.getPlayerRadius(), PropUtils.getPlayerHeight(), PropUtils.getPlayerMass());

	public User(String name, HostedConnection connection) {
		this.name = name;
		this.connection = connection;
		spatial.addControl(control);
	}

	public String getName() {
		return name;
	}

	public Spatial getSpatial() {
		return spatial;
	}

	public void setPosition(Vector3f position) {
		control.warp(position);
	}

	public Vector3f getPosition() {
		return spatial.getLocalTranslation();
	}

	public HostedConnection getConnection() {
		return connection;
	}

	public ConkCharacterControl getControl() {
		return control;
	}

}
