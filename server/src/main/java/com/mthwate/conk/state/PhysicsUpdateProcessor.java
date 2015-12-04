package com.mthwate.conk.state;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;
import com.mthwate.conk.world.Dimension;
import com.mthwate.datlib.math.vector.Vector3i;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author mthwate
 */
public class PhysicsUpdateProcessor implements Callable<List<PhysicsUpdate>> {

	private final Dimension dim;

	private final List<Vector3i> updates;

	public PhysicsUpdateProcessor(Dimension dim, List<Vector3i> updates) {
		this.dim = dim;
		this.updates = updates;
	}

	@Override
	public List<PhysicsUpdate> call() {
		List<PhysicsUpdate> nodeUpdates = new ArrayList<>();
		for (Vector3i pos : updates) {
			Node node = dim.genNode(pos);
			RigidBodyControl nodeControl = new RigidBodyControl(0.0f);
			node.addControl(nodeControl);
			nodeUpdates.add(new PhysicsUpdate(pos, node, nodeControl));
		}
		return nodeUpdates;
	}

}
