package com.mthwate.conk.state;

import com.jme3.app.state.AbstractAppState;
import com.jme3.math.Vector3f;
import com.mthwate.conk.message.PlayerPositionMessage;
import com.mthwate.conk.user.User;
import com.mthwate.conk.user.UserStore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class PositionAppState extends AbstractAppState {

	private final Map<String, Vector3f> cache = new HashMap<>();

	@Override
	public void update(float tpf) {
		for (User user : UserStore.getUsers()) {
			Vector3f oldPos = cache.get(user.getName());
			Vector3f newPos = user.getPosition().clone();
			if (oldPos == null || newPos.distance(oldPos) > 0.000001) {
				PlayerPositionMessage message = new PlayerPositionMessage(newPos);
				user.getConnection().send(message);
				cache.put(user.getName(), newPos);
			}
		}
	}

}
