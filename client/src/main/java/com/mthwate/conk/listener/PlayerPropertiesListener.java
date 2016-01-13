package com.mthwate.conk.listener;

import com.jme3.network.Client;
import com.mthwate.conk.message.PlayerPropertiesMessage;
import com.mthwate.conk.state.MovementAppState;

/**
 * @author mthwate
 */
public class PlayerPropertiesListener extends AbstractClientListener<PlayerPropertiesMessage> {

	private final MovementAppState movementAppState;

	public PlayerPropertiesListener(MovementAppState movementAppState) {
		this.movementAppState = movementAppState;
	}

	@Override
	protected void onReceived(Client source, PlayerPropertiesMessage m) {
		movementAppState.initControl(m.getRadius(), m.getHeight(), m.getMass());
	}

}
