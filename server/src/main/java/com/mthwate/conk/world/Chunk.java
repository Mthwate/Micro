package com.mthwate.conk.world;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.mthwate.conk.PropUtils;
import com.mthwate.conk.block.Block;
import com.mthwate.datlib.math.set.Set3i;
import jme3tools.optimize.GeometryBatchFactory;

/**
 * @author mthwate
 */
public class Chunk {

	public static final int CHUNK_SIZE = PropUtils.getChunkSize();

	private Block[][][] blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];

	private boolean changed = true;

	public Block get(int x, int y, int z) {
		return blocks[x][y][z];
	}

	public void set(Block block, int x, int y, int z) {
		changed = true;
		blocks[x][y][z] = block;
	}

	public Node genNode(Set3i pos) {
		Node node = new Node();

		for (int x = 0; x < CHUNK_SIZE; x++) {
			for (int y = 0; y < CHUNK_SIZE; y++) {
				for (int z = 0; z < CHUNK_SIZE; z++) {
					if (!blocks[x][y][z].getName().equals("air")) {//TODO change this
						Geometry geom = new Geometry();
						geom.setMesh(new Box(0.5f, 0.5f, 0.5f));
						geom.setLocalTranslation(x, y, z);
						node.attachChild(geom);
					}
				}
			}
		}

		GeometryBatchFactory.optimize(node);

		pos.multLocal(CHUNK_SIZE);

		node.setLocalTranslation(pos.getX(), pos.getY(), pos.getZ());

		return node;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public boolean hasChanged() {
		return changed;
	}
}
