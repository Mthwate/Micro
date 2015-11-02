package com.mthwate.conk.world;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.mthwate.conk.PropUtils;
import com.mthwate.conk.block.Block;
import com.mthwate.datlib.math.vector.Vector3i;
import jme3tools.optimize.GeometryBatchFactory;

/**
 * @author mthwate
 */
public class Chunk {

	public static final int CHUNK_SIZE = PropUtils.getChunkSize();

	private static final Box BOX_MESH = new Box(PropUtils.getCubeSize() / 2, PropUtils.getCubeSize() / 2, PropUtils.getCubeSize() / 2);

	private Block[][][] blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];

	private boolean changed = true;

	public Block get(int x, int y, int z) {
		return blocks[x][y][z];
	}

	public void set(Block block, int x, int y, int z) {
		changed = true;
		blocks[x][y][z] = block;
	}

	public Node genNode(Vector3i pos) {
		Node node = new Node();

		for (int x = 0; x < CHUNK_SIZE; x++) {
			for (int y = 0; y < CHUNK_SIZE; y++) {
				for (int z = 0; z < CHUNK_SIZE; z++) {
					if (blocks[x][y][z].isSolid()) {
						Geometry geom = new Geometry();
						geom.setMesh(BOX_MESH);
						geom.setLocalTranslation(x, y, z);
						node.attachChild(geom);
					}
				}
			}
		}

		GeometryBatchFactory.optimize(node);

		node.setLocalTranslation(pos.getX() * CHUNK_SIZE, pos.getY() * CHUNK_SIZE, pos.getZ() * CHUNK_SIZE);

		return node;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public boolean hasChanged() {
		return changed;
	}
}
