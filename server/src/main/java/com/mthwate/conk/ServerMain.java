package com.mthwate.conk;

import com.jme3.system.JmeContext;
import com.mthwate.conk.state.PhysicsUpdateAppState;
import com.mthwate.conk.state.WorldUpdateAppState;
import com.mthwate.conk.world.SaveUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * @author mthwate
 */
public class ServerMain {

	public static void main(String[] args) throws IOException {

		disableLog(WorldUpdateAppState.class);
		disableLog(PhysicsUpdateAppState.class);
		disableLog(SaveUtils.class);

		Starter.start(new ServerApp(), JmeContext.Type.Headless);
	}

	private static void disableLog(Class clazz) {
		LogManager.getLogManager().getLogger(LoggerFactory.getLogger(clazz).getName()).setLevel(Level.OFF);
	}

}
