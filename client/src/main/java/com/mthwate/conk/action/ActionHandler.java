package com.mthwate.conk.action;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.Trigger;

/**
 * @author mthwate
 */
public abstract class ActionHandler implements ActionListener {

	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		if (isPressed) {
			onPress(tpf);
		} else {
			onRelease(tpf);
		}
	}

	protected abstract Trigger getTrigger();

	public abstract void onPress(float tpf);

	public abstract void onRelease(float tpf);

}
