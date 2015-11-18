package com.mthwate.conk.command;

import com.mthwate.conk.ServerApp;

/**
 * @author mthwate
 */
public interface Command {

	String getName();

	void run(ServerApp app, String[] args);

}
