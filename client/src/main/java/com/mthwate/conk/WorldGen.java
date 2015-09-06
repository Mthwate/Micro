package com.mthwate.conk;

import com.mthwate.conk.info.BlockInfo;
import com.mthwate.conk.info.TextureInfo;

import java.util.Random;

/**
 * @author mthwate
 */
public class WorldGen {

	public static Chunk genChunk(int x, int y, int z, int size) {
		Chunk chunk = new Chunk(size);
		for (int ix = 0; ix < size; ix++) {
			for (int iy = 0; iy < size; iy++) {
				for (int iz = 0; iz < size; iz++) {
					int nx = x * size + ix;
					int ny = y * size + iy;
					int nz = z * size + iz;
					chunk.setBlock(ix, iy, iz, genBlock(nx, ny, nz));
				}
			}
		}
		return chunk;
	}

	private static Random rand = new Random();

	private static BlockInfo genBlock(int x, int y, int z) {
		BlockInfo block = null;

		Random rand1 = new Random(x + z);
		Random rand2 = new Random(x * z);

		if (y <= 0) {
			if (y == 0) {
				block = new BlockInfo(new TextureInfo("Grass" + (rand.nextInt(2) + 1), "Topsoil", "TopsoilSide"));
			} else if (y == -1) {
				block = new BlockInfo(new TextureInfo("Topsoil", "TopsoilSide"));
			} else if (y == -2) {
				block = new BlockInfo(new TextureInfo("ClayStone"));
			} else {
				block = new BlockInfo(new TextureInfo("Stone"));
			}
		} else if (y < 5 && rand1.nextInt(10) == 1 && rand2.nextInt(10) == 1) {
			block = new BlockInfo(new TextureInfo("OakLogTop", "OakLog"));
		} else if (y == 1 && rand1.nextInt(20) == 1 && rand2.nextInt(20) == 1) {
			block = new BlockInfo(new TextureInfo("Obelisk"), "Obelisk");
		}

		return block;
	}

}
