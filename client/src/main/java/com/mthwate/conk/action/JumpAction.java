package com.mthwate.conk.action;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.network.Client;
import com.mthwate.conk.message.JumpMessage;

/**
 * @author mthwate
 */
public class JumpAction extends ActionHandler {

	private final Client client;

	public JumpAction(Client client) {
		this.client = client;
	}

	@Override
	protected Trigger getTrigger() {
		return new KeyTrigger(KeyInput.KEY_SPACE);
	}

	@Override
	public void onPress(float tpf) {

		client.send(new JumpMessage());
	}

	@Override
	public void onRelease(float tpf) {}

}
