package com.mthwate.conk.action;

import com.jme3.input.InputManager;

/**
 * @author mthwate
 */
public class ActionUtils {

	private static int count = 0;

	public static void register(InputManager inputManager, ActionHandler actionHandler) {
		String name = "KeyMapping_" + count++;
		inputManager.addMapping(name, actionHandler.getTrigger());
		inputManager.addListener(actionHandler, name);
	}

}
