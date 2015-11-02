package com.mthwate.conk.action;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.mthwate.conk.state.MovementAppState;

/**
 * @author mthwate
 */
public class MoveRightAction extends ActionHandler {

	@Override
	protected Trigger getTrigger() {
		return new KeyTrigger(KeyInput.KEY_D);
	}

	@Override
	public void onPress(float tpf) {
		MovementAppState.setRight(true);
	}

	@Override
	public void onRelease(float tpf) {
		MovementAppState.setRight(false);
	}

}
