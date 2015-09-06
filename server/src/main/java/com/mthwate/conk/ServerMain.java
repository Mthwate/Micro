package com.mthwate.conk;

import com.jme3.system.JmeContext;

/**
 * @author mthwate
 */
public class ServerMain {

	public static void main(String[] args) {
		Starter.start(new ServerApp(), JmeContext.Type.Headless);
	}

}
