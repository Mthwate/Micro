package com.mthwate.conk.message;

import com.jme3.network.serializing.Serializer;

/**
 * @author mthwate
 */
public class MessageUtils {

	public static void register() {
		Serializer.registerClass(BlockUpdateMessage.class);
		Serializer.registerClass(LoginMessage.class);
		Serializer.registerClass(PlayerPositionMessage.class);
		Serializer.registerClass(JumpMessage.class);
		Serializer.registerClass(MoveMessage.class);
		Serializer.registerClass(BlockBreakMessage.class);
		Serializer.registerClass(ClearGroupMessage.class);
		Serializer.registerClass(ChunkUpdateMessage.class);
		Serializer.registerClass(PlayerPropertiesMessage.class);
	}

}
