package com.mthwate.conk.action;

import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.mthwate.conk.World;
import com.mthwate.conk.info.BlockInfo;
import com.mthwate.conk.info.TextureInfo;
import com.mthwate.datlib.math.vector.Vector3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mthwate
 */
public class RightClickAction extends ActionHandler {

	private static final Logger log = LoggerFactory.getLogger(RightClickAction.class);

	private final Camera cam;

	private final World world;

	public RightClickAction(Camera cam, World world) {
		this.cam = cam;
		this.world = world;
	}

	@Override
	protected Trigger getTrigger() {
		return new MouseButtonTrigger(MouseInput.BUTTON_RIGHT);
	}

	@Override
	public void onPress(float tpf) {
		CollisionResults results = new CollisionResults();
		Ray ray = new Ray(cam.getLocation(), cam.getDirection());
		world.getNode().collideWith(ray, results);
		if (results.size() > 0) {
			Vector3f pos = results.getClosestCollision().getContactPoint();
			Vector3i coords = new Vector3i(0, 0, 0);
			for (int i = 0; i < 3; i++) {
				int coord = pick(pos.get(i), cam.getLocation().get(i));
				switch (i) {
					case 0: coords = coords.add(coord, 0, 0); break;
					case 1: coords = coords.add(0, coord, 0); break;
					case 2: coords = coords.add(0, 0, coord); break;
				}
			}
			world.setBlock(coords.getX(), coords.getY(), coords.getZ(), new BlockInfo(new TextureInfo("ceramicTile")));
		}
	}

	private static int pick(float f, float c) {
		int result = (int) Math.floor(f + 0.5);
		if (c < f && (Math.abs(Math.abs(f - ((int) f)) - 0.5) < 0.00001)) {
			result--;
		}
		return result;
	}

	@Override
	public void onRelease(float tpf) {}
}
