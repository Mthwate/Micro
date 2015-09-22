package com.mthwate.conk;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.mthwate.conk.info.BlockInfo;
import com.mthwate.datlib.math.set.Set3i;
import jme3tools.optimize.GeometryBatchFactory;

import java.util.List;

/**
 * @author mthwate
 */
public class Chunk {

	private final int size;

	private final BlockInfo[][][] blocks;

	private boolean changed = false;

	public Chunk(int size) {
		this.size = size;
		blocks = new BlockInfo[size][size][size];
	}

	public void markChanged() {
		changed = true;
	}

	public boolean hasChanged() {
		return changed;
	}

	public void setBlock(int x, int y, int z, BlockInfo block) {
		changed = true;
		blocks[x][y][z] = block;
	}

	public BlockInfo getBlock(int x, int y, int z) {
		return blocks[x][y][z];
	}

	public Node genNode(AssetManager assetManager, World world, Set3i pos, List<LightMap.LightChunk> light) {

		int cx = pos.getX();
		int cy = pos.getY();
		int cz = pos.getZ();

		changed = false;

		Node node = new Node();

		BlockWrapper[][][] wrappers = new BlockWrapper[size][size][size];

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				for (int z = 0; z < size; z++) {
					wrappers[x][y][z] = new BlockWrapper(blocks[x][y][z]);
					for (LightMap.LightChunk lightMap : light) {
						wrappers[x][y][z].addLight(lightMap.getLight(x, y, z));
					}
				}
			}
		}

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				for (int z = 0; z < size; z++) {
					BlockWrapper block = wrappers[x][y][z];
					if (block.getInfo() != null) {
						tryDel(block, x, y + 1, z, Side.TOP, world, cx, cy, cz, wrappers);
						tryDel(block, x, y - 1, z, Side.BOTTOM, world, cx, cy, cz, wrappers);
						tryDel(block, x, y, z + 1, Side.FRONT, world, cx, cy, cz, wrappers);
						tryDel(block, x, y, z - 1, Side.BACK, world, cx, cy, cz, wrappers);
						tryDel(block, x + 1, y, z, Side.RIGHT, world, cx, cy, cz, wrappers);
						tryDel(block, x - 1, y, z, Side.LEFT, world, cx, cy, cz, wrappers);
						addBlock(node, x, y, z, assetManager, wrappers);
					}
				}
			}
		}

		GeometryBatchFactory.optimize(node);

		node.setName(pos.toString());

		pos.multLocal(size);

		node.setLocalTranslation(pos.getX(), pos.getY(), pos.getZ());

		return node;
	}

	private void tryDel(BlockWrapper block, int xf, int yf, int zf, Side side, World world, int cx, int cy, int cz, BlockWrapper[][][] wrappers) {
		if (xf >= 0 && xf < size && yf >= 0 && yf < size && zf >= 0 && zf < size) {
			BlockWrapper adj = wrappers[xf][yf][zf];
			if (adj.getInfo() != null && !adj.getInfo().getTextureInfo().isTransparent() && adj.getInfo().getModel() == null) {
				block.disableSide(side);
			}
		} else {
			BlockInfo adj = world.getBlock(cx * size + xf, cy * size + yf, cz * size + zf);
			if (adj != null && !adj.getTextureInfo().isTransparent() && adj.getModel() == null) {
				block.disableSide(side);
			}
		}
	}

	private static final float[][] sideConfig = new float[6][6];

	static {
		sideConfig[Side.TOP.getI()][0] = -0.5f;
		sideConfig[Side.TOP.getI()][1] = 0.5f;
		sideConfig[Side.TOP.getI()][2] = 0.5f;
		sideConfig[Side.TOP.getI()][3] = -FastMath.HALF_PI;
		sideConfig[Side.TOP.getI()][4] = 0;

		sideConfig[Side.BOTTOM.getI()][0] = -0.5f;
		sideConfig[Side.BOTTOM.getI()][1] = -0.5f;
		sideConfig[Side.BOTTOM.getI()][2] = -0.5f;
		sideConfig[Side.BOTTOM.getI()][3] = FastMath.HALF_PI;
		sideConfig[Side.BOTTOM.getI()][4] = 0;

		sideConfig[Side.FRONT.getI()][0] = -0.5f;
		sideConfig[Side.FRONT.getI()][1] = -0.5f;
		sideConfig[Side.FRONT.getI()][2] = 0.5f;
		sideConfig[Side.FRONT.getI()][3] = 0;
		sideConfig[Side.FRONT.getI()][4] = 0;

		sideConfig[Side.BACK.getI()][0] = 0.5f;
		sideConfig[Side.BACK.getI()][1] = -0.5f;
		sideConfig[Side.BACK.getI()][2] = -0.5f;
		sideConfig[Side.BACK.getI()][3] = 0;
		sideConfig[Side.BACK.getI()][4] = FastMath.PI;

		sideConfig[Side.RIGHT.getI()][0] = 0.5f;
		sideConfig[Side.RIGHT.getI()][1] = -0.5f;
		sideConfig[Side.RIGHT.getI()][2] = 0.5f;
		sideConfig[Side.RIGHT.getI()][3] = 0;
		sideConfig[Side.RIGHT.getI()][4] = FastMath.HALF_PI;

		sideConfig[Side.LEFT.getI()][0] = -0.5f;
		sideConfig[Side.LEFT.getI()][1] = -0.5f;
		sideConfig[Side.LEFT.getI()][2] = -0.5f;
		sideConfig[Side.LEFT.getI()][3] = 0;
		sideConfig[Side.LEFT.getI()][4] = -FastMath.HALF_PI;

		for (int i = 0; i < 6; i++) {
			sideConfig[i][5] = 0;
		}
	}

	private void addBlock(Node node, int x, int y, int z, AssetManager assetManager, BlockWrapper[][][] wrappers) {

		BlockWrapper block = wrappers[x][y][z];

		if (block.getInfo().getModel() == null) {
			for (int i = 0; i < 6; i++) {
				if (block.isEnabled(i)) {

					ColorRGBA light = null;

					switch (i) {
						case 0: light = getLight(wrappers ,x, y + 1, z); break;
						case 1: light = getLight(wrappers, x, y - 1, z); break;
						case 2: light = getLight(wrappers, x, y, z + 1); break;
						case 3: light = getLight(wrappers, x, y, z - 1); break;
						case 4: light = getLight(wrappers, x - 1, y, z); break;
						case 5: light = getLight(wrappers, x + 1, y, z); break;
					}


					Spatial spatial = AssetStore.getSpatial(assetManager, block.getInfo(), Side.getSide(i), light);
					spatial.setLocalTranslation(x + sideConfig[i][0], y + sideConfig[i][1], z + sideConfig[i][2]);
					spatial.rotate(sideConfig[i][3], sideConfig[i][4], sideConfig[i][5]);
					node.attachChild(spatial);
				}
			}
		} else {
			boolean enabled = false;
			for (int i = 0; i < 6 && !enabled; i++) {
				if (block.isEnabled(i)) {
					enabled = true;
				}
			}
			if (enabled) {
				Spatial spatial = AssetStore.getSpatial(assetManager, block.getInfo(), Side.TOP, ColorRGBA.White);
				spatial.setLocalTranslation(x, y, z);
				node.attachChild(spatial);
			}
		}
	}

	private ColorRGBA getLight(BlockWrapper[][][] wrappers, int x, int y, int z) {
		ColorRGBA light = ColorRGBA.Black;
		if (x >= 0 && x < size && y >= 0 && y < size && z >= 0 && z < size) {
			light = wrappers[x][y][z].getLight();
		}
		return light;
	}

}
