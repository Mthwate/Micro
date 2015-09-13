package com.mthwate.conk.message;

import com.jme3.network.serializing.Serializable;
import com.jme3.network.serializing.Serializer;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author mthwate
 */
public class MessageUtils {

	private static final Logger log = LoggerFactory.getLogger(MessageUtils.class);

	public static void register() {
		Serializer.registerClass(BlockUpdateMessage.class);
		Serializer.registerClass(LoginMessage.class);
		Serializer.registerClass(PlayerPositionMessage.class);
	}

}
