package com.mthwate.conk;

import com.jme3.math.Vector3f;
import com.mthwate.datlib.Timer;
import com.mthwate.datlib.math.set.Set3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mthwate
 */
public class LightMap {

	private static final Logger log = LoggerFactory.getLogger(LightMap.class);

	private final Set3i pos;

	private final Vector3f light;

	private final float fallOff;

	private final int size;

	public LightMap(Set3i pos, Vector3f light, float fallOff, int size) {
		this.pos = pos;
		this.light = light;
		this.fallOff = fallOff;
		this.size = size;
	}

	private TmpMap cacheMap;

	public boolean isInit() {
		return cacheMap != null;
	}

	public boolean contains(World world, Set3i pos) {
		return cacheMap.getChunk(pos) != null;
	}

	public LightChunk getChunk(World world, Set3i pos) {
		return cacheMap.getChunk(pos);
	}

	public void generate(World world) {
		cacheMap = new TmpMap();
		Timer timer = new Timer();
		crawl(world, pos.getX(), pos.getY(), pos.getZ(), light);
		log.info("Took {} sec to generate light map", timer.getSec(2));
	}

	private void crawl(World world, int ox, int oy, int oz, Vector3f oVal) {

		float[] fallOffs = new float[3];

		for (int i = 0; i < 3; i++) {
			fallOffs[i] = (float) (fallOff * Math.sqrt(i+1));
		}


		LinkedHashMap<Set3i, Vector3f> posval = new LinkedHashMap<>();

		posval.put(new Set3i(ox, oy, oz), oVal);

		while (posval.size() > 0) {

			Map.Entry<Set3i, Vector3f> next = posval.entrySet().iterator().next();

			Set3i pos = next.getKey();
			Vector3f val = next.getValue();
			posval.remove(pos);

			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			if ((world.getBlock(x, y, z) == null || pos.equals(this.pos)) && val.length() > 0 && cacheMap.getLight(x, y, z).length() < val.length()) {
				cacheMap.setLight(x, y, z, val);

				if (val.length() > 0) {
					Vector3f[] newVals = new Vector3f[3];

					for (int i = 0; i < 3; i++) {
						newVals[i] = val.subtract(fallOffs[i], fallOffs[i], fallOffs[i]);
					}

					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							if (newVals[i].get(j) < 0) {
								newVals[i].set(j, 0);
							}
						}
					}


					int[] iCoords = new int[3];//ix, iy, and iz

					for (iCoords[0] = -1; iCoords[0] <= 1; iCoords[0]++) {
						for (iCoords[1] = -1; iCoords[1] <= 1; iCoords[1]++) {
							for (iCoords[2] = -1; iCoords[2] <= 1; iCoords[2]++) {

								int nonZero = 0;

								for (int i = 0; i < 3; i++) {
									if (iCoords[i] != 0) {
										nonZero++;
									}
								}

								if (nonZero == 1) {//TODO change to i > 0

									Set3i npos = new Set3i(x + iCoords[0], y + iCoords[1], z + iCoords[2]);

									Vector3f nval = newVals[nonZero - 1];

									if (!posval.containsKey(npos) || posval.get(npos).length() < nval.length()) {
										posval.put(npos, nval);
									}
								}
							}
						}
					}


				}



			}
		}
	}

	public void delete(World world) {
		for (Set3i pos :cacheMap.getMap().keySet()) {
			world.updateMarkChunk(pos);
		}
	}

	private class TmpMap {

		private final Map<Set3i, LightChunk> map = new HashMap<>();

		private Map<Set3i, LightChunk> getMap() {
			return map;
		}

		private void setLight(int x, int y, int z, Vector3f val) {
			Set3i cPos = PositionUtils.getChunkFromGlobal(x, y, z, size);

			LightChunk chunk = getGenChunk(cPos);

			int lx = PositionUtils.getLocalFromGlobal(x, size);
			int ly = PositionUtils.getLocalFromGlobal(y, size);
			int lz = PositionUtils.getLocalFromGlobal(z, size);

			chunk.setLight(lx, ly, lz, val);
		}

		private Vector3f getLight(int x, int y, int z) {
			Set3i cPos = PositionUtils.getChunkFromGlobal(x, y, z, size);

			LightChunk chunk = getGenChunk(cPos);

			int lx = PositionUtils.getLocalFromGlobal(x, size);
			int ly = PositionUtils.getLocalFromGlobal(y, size);
			int lz = PositionUtils.getLocalFromGlobal(z, size);

			return chunk.getLight(lx, ly, lz);
		}

		public LightChunk getGenChunk(Set3i pos) {
			LightChunk chunk = map.get(pos);

			if (chunk == null) {
				chunk = new LightChunk(size);
				map.put(pos, chunk);
			}

			return chunk;
		}

		public LightChunk getChunk(Set3i pos) {
			return map.get(pos);
		}
	}

}
