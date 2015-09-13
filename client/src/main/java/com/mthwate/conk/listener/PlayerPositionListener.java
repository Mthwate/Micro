package com.mthwate.conk.listener;

import com.jme3.network.Client;
import com.jme3.renderer.Camera;
import com.mthwate.conk.message.PlayerPositionMessage;

/**
 * @author mthwate
 */
public class PlayerPositionListener extends AbstractClientListener<PlayerPositionMessage>  {

	private final Camera cam;

	public PlayerPositionListener(Camera cam) {
		this.cam = cam;
	}

	@Override
	protected void onReceived(Client source, PlayerPositionMessage m) {
		cam.setLocation(m.getPosition().add(0, 1.75f - 0.1f, 0));
	}

}
