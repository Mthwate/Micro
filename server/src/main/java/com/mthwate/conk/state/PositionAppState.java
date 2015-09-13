package com.mthwate.conk.state;

import com.jme3.app.state.AbstractAppState;
import com.mthwate.conk.message.PlayerPositionMessage;
import com.mthwate.conk.user.User;
import com.mthwate.conk.user.UserStore;

/**
 * @author mthwate
 */
public class PositionAppState extends AbstractAppState {

	@Override
	public void update(float tpf) {
		for (User user : UserStore.getUsers()) {
			user.getConnection().send(new PlayerPositionMessage(user.getPosition()));
		}
	}

}
