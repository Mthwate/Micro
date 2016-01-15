package com.mthwate.conk.physics;

import com.jme3.bullet.control.BetterCharacterControl;

/**
 * @author mthwate
 */
public class ConkCharacterControl extends BetterCharacterControl {

	public ConkCharacterControl(float radius, float height, float mass) {
		super(radius, height, mass);
	}

	public void setFriction(float friction) {
		this.rigidBody.setFriction(friction);
	}

}
