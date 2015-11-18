package com.mthwate.conk.command;

import com.mthwate.conk.ServerApp;

/**
 * @author mthwate
 */
@Exec
public class CommandStop implements Command {

	@Override
	public String getName() {
		return "stop";
	}

	@Override
	public void run(ServerApp app, String[] args) {
		app.stop();
	}

}
