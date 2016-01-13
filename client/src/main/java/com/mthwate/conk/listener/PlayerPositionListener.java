package com.mthwate.conk.listener;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.network.Client;
import com.jme3.scene.Node;
import com.mthwate.conk.message.PlayerPositionMessage;

/**
 * @author mthwate
 */
public class PlayerPositionListener extends AbstractClientListener<PlayerPositionMessage>  {

	private final Node camNode;

	private final BitmapText hudText;

	public PlayerPositionListener(Node camNode, Node guiNode, BitmapFont guiFont) {
		this.camNode = camNode;

		hudText = new BitmapText(guiFont, false);
		hudText.setSize(guiFont.getCharSet().getRenderedSize());
		hudText.setColor(ColorRGBA.White);
		hudText.setLocalTranslation(300, hudText.getLineHeight(), 0);
		guiNode.attachChild(hudText);
	}

	@Override
	protected void onReceived(Client source, PlayerPositionMessage m) {
		camNode.setLocalTranslation(m.getPosition());
		hudText.setText(String.format("(%.2f, %.2f, %.2f)", camNode.getLocalTranslation().getX(), camNode.getLocalTranslation().getY(), camNode.getLocalTranslation().getZ()));
	}

}
