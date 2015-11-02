package com.mthwate.conk.action;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.mthwate.conk.state.MovementAppState;

/**
 * @author mthwate
 */
public class MoveLeftAction extends ActionHandler {

	@Override
	protected Trigger getTrigger() {
		return new KeyTrigger(KeyInput.KEY_A);
	}

	@Override
	public void onPress(float tpf) {
		MovementAppState.setLeft(true);
	}

	@Override
	public void onRelease(float tpf) {
		MovementAppState.setLeft(false);
	}

}
