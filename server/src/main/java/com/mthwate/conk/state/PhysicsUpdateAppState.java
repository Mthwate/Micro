package com.mthwate.conk.state;

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
import com.mthwate.conk.world.DimensionStore;
import com.mthwate.datlib.Timer;
import com.mthwate.datlib.math.vector.Vector3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author mthwate
 */
public class PhysicsUpdateAppState extends TimedAppState {

	private static final Logger log = LoggerFactory.getLogger(PhysicsUpdateAppState.class);

	private final BulletAppState bulletAppState;

	private final Node rootNode;

	private final Map<Vector3i, Node> chunks = new HashMap<>();

	private final Dimension dim = DimensionStore.getDimension();//TODO remove this

	private long lastTimeTick = 0;

	private long lastTimeThread = 0;

	private static final int INNER_RADIUS = 1;

	private static final int OUTER_RADIUS = 2;

	private final ExecutorService executor = Executors.newSingleThreadExecutor();

	private Future<List<PhysicsUpdate>> future;

	public PhysicsUpdateAppState(BulletAppState bulletAppState, Node rootNode) {
		this.bulletAppState = bulletAppState;
		this.rootNode = rootNode;
	}

	@Override
	public void timedUpdate(float tpf) {

		long nowTick = System.nanoTime();

		statistics.set("startCache", chunks.size());

		for (User user : UserStore.INSTANCE.getUsers()) {

			Vector3f pos = user.getPosition();

			int cx = PositionUtils.INSTANCE.getChunkFromGlobal((int) pos.getX(), Chunk.CHUNK_SIZE);
			int cy = PositionUtils.INSTANCE.getChunkFromGlobal((int) pos.getY(), Chunk.CHUNK_SIZE);
			int cz = PositionUtils.INSTANCE.getChunkFromGlobal((int) pos.getZ(), Chunk.CHUNK_SIZE);

			for (int x = -INNER_RADIUS; x <= INNER_RADIUS; x++) {
				for (int y = -INNER_RADIUS; y <= INNER_RADIUS; y++) {
					for (int z = -INNER_RADIUS; z <= INNER_RADIUS; z++) {
						Vector3i cPos = new Vector3i(cx + x, cy + y, cz + z);
						if (dim.hasUpdated(cPos, lastTimeTick) || !chunks.containsKey(cPos)) {
							statistics.add("forced", 1);
							updateChunk(cPos, dim.genNode(cPos));
						}
					}
				}
			}

			if (user.getControl().isOnGround()) {
				user.getControl().setFriction(0.5f);
			} else {
				user.getControl().setFriction(0);
			}
		}

		if (future == null) {

			statistics.set("future", false);

			long nowThread = System.nanoTime();

			List<Vector3i> updates = new ArrayList<>();

			for (User user : UserStore.INSTANCE.getUsers()) {

				Vector3f pos = user.getPosition();

				int cx = PositionUtils.INSTANCE.getChunkFromGlobal((int) pos.getX(), Chunk.CHUNK_SIZE);
				int cy = PositionUtils.INSTANCE.getChunkFromGlobal((int) pos.getY(), Chunk.CHUNK_SIZE);
				int cz = PositionUtils.INSTANCE.getChunkFromGlobal((int) pos.getZ(), Chunk.CHUNK_SIZE);

				for (int x = -OUTER_RADIUS; x <= OUTER_RADIUS; x++) {
					for (int y = -OUTER_RADIUS; y <= OUTER_RADIUS; y++) {
						for (int z = -OUTER_RADIUS; z <= OUTER_RADIUS; z++) {
							Vector3i cPos = new Vector3i(cx + x, cy + y, cz + z);
							if (dim.hasUpdated(cPos, lastTimeThread) || !chunks.containsKey(cPos)) {
								updates.add(cPos);
							}
						}
					}
				}
			}

			future = executor.submit(new PhysicsUpdateProcessor(dim, updates));

			lastTimeThread = nowThread;
		} else {

			statistics.set("future", true);

			if (future.isDone()) {
				try {
					List<PhysicsUpdate> updates = future.get();
					for (PhysicsUpdate update : updates) {
						updateChunk(update.getPos(), update.getNode(), update.getControl());
					}
					future = null;
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		}

		statistics.set("endCache", chunks.size());

		lastTimeTick = nowTick;
	}

	private void updateChunk(Vector3i pos, Node node) {
		RigidBodyControl nodeControl = new RigidBodyControl(0.0f);
		node.addControl(nodeControl);
		updateChunk(pos, node, nodeControl);
	}

	private void updateChunk(Vector3i pos, Node node, RigidBodyControl nodeControl) {
		Timer totalTimer = new Timer();

		log.info("Updating rigid body of chunk {}", pos);

		statistics.add("updates", 1);

		Timer clearTimer = new Timer();

		if (chunks.containsKey(pos)) {
			for (int i = 0; i < chunks.get(pos).getNumControls(); i++) {
				Control control = chunks.get(pos).getControl(i);
				chunks.get(pos).removeControl(control);
				bulletAppState.getPhysicsSpace().remove(control);
			}
			chunks.get(pos).removeFromParent();
		}

		chunks.remove(pos);

		statistics.add("clearTime", clearTimer.getNano());

		Timer addTimer = new Timer();

		bulletAppState.getPhysicsSpace().add(nodeControl);
		rootNode.attachChild(node);

		chunks.put(pos, node);

		statistics.add("addTime", addTimer.getNano());

		statistics.add("updateTime", totalTimer.getNano());
	}

	@Override
	protected long getMaxTime() {
		return 1000000;
	}

}
