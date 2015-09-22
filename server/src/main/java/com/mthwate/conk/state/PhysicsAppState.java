package com.mthwate.conk.state;

import com.jme3.app.state.AbstractAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.control.Control;
import com.mthwate.conk.PositionUtils;
import com.mthwate.conk.user.User;
import com.mthwate.conk.user.UserStore;
import com.mthwate.conk.world.Chunk;
import com.mthwate.conk.world.Dimension;
import com.mthwate.conk.world.generator.RandomWorldGenerator;
import com.mthwate.datlib.math.set.Set3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mthwate
 */
public class PhysicsAppState extends AbstractAppState {

	private static final Logger log = LoggerFactory.getLogger(PhysicsAppState.class);

	private final BulletAppState bulletAppState;

	private final Node rootNode;

	private final Map<Set3i, Node> chunks = new HashMap<>();

	private final Dimension dim = new Dimension("world", new RandomWorldGenerator());//TODO remove this

	public PhysicsAppState(BulletAppState bulletAppState, Node rootNode) {
		this.bulletAppState = bulletAppState;
		this.rootNode = rootNode;
	}

	@Override
	public void update(float tpf) {

		List<Set3i> chunkPos = new ArrayList<>();

		for (User user : UserStore.getUsers()) {
			Vector3f pos = user.getPosition();

			int cx = PositionUtils.getChunkFromGlobal((int) pos.getX(), Chunk.CHUNK_SIZE);
			int cy = PositionUtils.getChunkFromGlobal((int) pos.getY(), Chunk.CHUNK_SIZE);
			int cz = PositionUtils.getChunkFromGlobal((int) pos.getZ(), Chunk.CHUNK_SIZE);

			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						chunkPos.add(new Set3i(cx + x, cy + y, cz + z));
					}
				}
			}
		}

		for (Set3i pos : chunkPos) {

			if (dim.hasChanged(pos)) {
				dim.setChanged(pos, false);

				if (chunks.containsKey(pos)) {
					chunks.get(pos).removeFromParent();
					for (int i = 0; i < chunks.get(pos).getNumControls(); i++) {
						Control control = chunks.get(pos).getControl(i);
						chunks.get(pos).removeControl(control);
						bulletAppState.getPhysicsSpace().remove(control);
					}
				}


				chunks.remove(pos);

				RigidBodyControl nodeControl = new RigidBodyControl(0.0f);
				Node chunkNode = dim.genNode(pos);

				chunkNode.addControl(nodeControl);
				bulletAppState.getPhysicsSpace().add(nodeControl);
				rootNode.attachChild(chunkNode);

				chunks.put(pos, chunkNode);
			}

		}
	}

}
