package com.mthwate.conk.listener;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.network.Client;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.mthwate.conk.message.PlayerPositionMessage;

/**
 * @author mthwate
 */
public class PlayerPositionListener extends AbstractClientListener<PlayerPositionMessage>  {

	private final Camera cam;

	private final BitmapText hudText;

	public PlayerPositionListener(Camera cam, Node guiNode, BitmapFont guiFont) {
		this.cam = cam;

		hudText = new BitmapText(guiFont, false);
		hudText.setSize(guiFont.getCharSet().getRenderedSize());
		hudText.setColor(ColorRGBA.White);
		hudText.setLocalTranslation(300, hudText.getLineHeight(), 0);
		guiNode.attachChild(hudText);
	}

	@Override
	protected void onReceived(Client source, PlayerPositionMessage m) {
		cam.setLocation(m.getPosition().add(0, 1.75f - 0.1f, 0));

		hudText.setText(String.format("(%.2f, %.2f, %.2f)", cam.getLocation().getX(), cam.getLocation().getY(), cam.getLocation().getZ()));
	}

}
