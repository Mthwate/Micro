package com.mthwate.conk.user;

import com.jme3.math.Vector3f;

/**
 * @author mthwate
 */
public class User {

	private final String name;

	private Vector3f position;

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getPosition() {
		return position;
	}
}
