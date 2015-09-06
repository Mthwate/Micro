package com.mthwate.conk.action;

import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.mthwate.conk.World;
import com.mthwate.datlib.math.set.Set3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mthwate
 */
public class LeftClickAction extends ActionHandler {

	private static final Logger log = LoggerFactory.getLogger(LeftClickAction.class);

	private final Camera cam;

	private final World world;

	public LeftClickAction(Camera cam, World world) {
		this.cam = cam;
		this.world = world;
	}

	@Override
	protected Trigger getTrigger() {
		return new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
	}

	@Override
	public void onPress(float tpf) {
		CollisionResults results = new CollisionResults();
		Ray ray = new Ray(cam.getLocation(), cam.getDirection());
		world.getNode().collideWith(ray, results);
		if (results.size() > 0) {
			Vector3f pos = results.getClosestCollision().getContactPoint();
			log.info("Collision at {}", pos);
			Set3i coords = new Set3i(0, 0, 0);
			for (int i = 0; i < 3; i++) {
				int coord = (int) (pos.get(i) + 0.5);
				if (Math.abs(Math.abs(pos.get(i) - coord) - 0.5) < 0.000001) {
					coord = (int) pos.get(i);
					if (cam.getLocation().get(i) < pos.get(i)) {
						coord++;
					}
				}
				switch (i) {
					case 0: coords.addLocal(coord, 0, 0); break;
					case 1: coords.addLocal(0, coord, 0); break;
					case 2: coords.addLocal(0, 0, coord); break;
				}
			}
			log.info("Block at {}", coords);
			world.setBlock(coords.getX(), coords.getY(), coords.getZ(), null);
		}
	}

	@Override
	public void onRelease(float tpf) {}
}
