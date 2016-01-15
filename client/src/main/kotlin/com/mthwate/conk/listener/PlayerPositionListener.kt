package com.mthwate.conk.listener

import com.jme3.font.BitmapFont
import com.jme3.font.BitmapText
import com.jme3.math.ColorRGBA
import com.jme3.network.Client
import com.jme3.scene.Node
import com.mthwate.conk.message.PlayerPositionMessage

/**
 * @author mthwate
 */
class PlayerPositionListener(private val camNode: Node, guiNode: Node, guiFont: BitmapFont) : AbstractClientListener<PlayerPositionMessage>() {

	private val hudText: BitmapText

	init {
		hudText = BitmapText(guiFont, false)
		hudText.size = guiFont.charSet.renderedSize.toFloat()
		hudText.color = ColorRGBA.White
		hudText.setLocalTranslation(300f, hudText.lineHeight, 0f)
		guiNode.attachChild(hudText)
	}

	override fun onReceived(src: Client, msg: PlayerPositionMessage) {
		val pos = msg.position
		camNode.localTranslation = pos
		hudText.text = "(${pos.x}, ${pos.y}, ${pos.z})"
	}

}