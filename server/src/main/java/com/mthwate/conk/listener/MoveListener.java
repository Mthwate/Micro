package com.mthwate.conk.listener;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.mthwate.conk.message.MoveMessage;
import com.mthwate.conk.user.UserStore;

/**
 * @author mthwate
 */
public class MoveListener extends AbstractServerListener<MoveMessage> {

	@Override
	protected void onReceived(HostedConnection source, MoveMessage m) {
		BetterCharacterControl conrol = UserStore.getUser(source).getControl();
		conrol.setWalkDirection(new Vector3f(m.getX(), 0, m.getZ()));
	}

}
