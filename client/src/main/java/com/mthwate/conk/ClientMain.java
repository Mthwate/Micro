package com.mthwate.conk;

import com.jme3.system.JmeContext;

/**
 * @author mthwate
 */
public class ClientMain {

	public static void main(String[] args) {
		Starter.start(new ClientApp(), JmeContext.Type.Display);
	}

}
