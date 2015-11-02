package com.mthwate.conk.action;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.mthwate.conk.state.MovementAppState;

/**
 * @author mthwate
 */
public class MoveForwardAction extends ActionHandler {

	@Override
	protected Trigger getTrigger() {
		return new KeyTrigger(KeyInput.KEY_W);
	}

	@Override
	public void onPress(float tpf) {
		MovementAppState.setForward(true);
	}

	@Override
	public void onRelease(float tpf) {
		MovementAppState.setForward(false);
	}

}
