package com.mthwate.conk.listener;

import com.jme3.network.Client;
import com.mthwate.conk.WorldStore;
import com.mthwate.conk.message.ClearGroupMessage;
import com.mthwate.datlib.math.vector.Vector3i;

/**
 * @author mthwate
 */
public class ClearGroupListener extends AbstractClientListener<ClearGroupMessage> {

	@Override
	protected void onReceived(Client source, ClearGroupMessage m) {

		Vector3i start = m.getStart();
		Vector3i end = m.getEnd();

		for (int x = start.getX(); x < end.getX(); x++) {
			for (int y = start.getY(); y < end.getY(); y++) {
				for (int z = start.getZ(); z < end.getZ(); z++) {
					WorldStore.getWorld().setBlock(x, y, z, null);
				}
			}
		}

	}

}
