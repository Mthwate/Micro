package com.mthwate.conk.command;

import com.jme3.math.Vector3f;
import com.mthwate.conk.ServerApp;
import com.mthwate.conk.user.User;
import com.mthwate.conk.user.UserStore;

/**
 * @author mthwate
 */
@Exec
public class CommandTp implements Command {

	@Override
	public String getName() {
		return "tp";
	}

	@Override
	public void run(ServerApp app, String[] args) {
		if (args.length == 2) {
			User src = UserStore.getUser(args[0]);
			User dst = UserStore.getUser(args[1]);
			src.getControl().warp(dst.getPosition());
		} else if (args.length == 4) {
			User src = UserStore.getUser(args[0]);
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			int z = Integer.parseInt(args[3]);
			src.getControl().warp(new Vector3f(x, y, z));
		}
	}

}
