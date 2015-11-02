package com.mthwate.conk.listener;

import com.jme3.network.HostedConnection;
import com.mthwate.conk.message.JumpMessage;
import com.mthwate.conk.user.UserStore;

/**
 * @author mthwate
 */
public class JumpListener extends AbstractServerListener<JumpMessage> {

	@Override
	protected void onReceived(HostedConnection source, JumpMessage m) {
		UserStore.getUser(source).getControl().jump();
	}

}
