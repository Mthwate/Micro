package com.mthwate.conk;

import com.jme3.math.Vector3f;
import com.mthwate.datlib.Timer;
import com.mthwate.datlib.math.set.Set3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
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

		float fallOff2 = (float) (fallOff * Math.sqrt(2));
		float fallOff3 = (float) (fallOff * Math.sqrt(3));



		LinkedList<Set3i> poss = new LinkedList<>();
		LinkedList<Vector3f> vals = new LinkedList<>();

		poss.add(new Set3i(ox, oy, oz));
		vals.add(oVal);

		while (poss.size() > 0) {

			Set3i pos = poss.removeFirst();
			Vector3f val = vals.removeFirst();

			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			if ((world.getBlock(x, y, z) == null || pos.equals(this.pos)) && val.length() > 0 && cacheMap.getLight(x, y, z).length() < val.length()) {
				cacheMap.setLight(x, y, z, val);

				if (val.length() > 0) {
					Vector3f nVal1 = val.subtract(fallOff, fallOff, fallOff);
					Vector3f nVal2 = val.subtract(fallOff2, fallOff2, fallOff2);
					Vector3f nVal3 = val.subtract(fallOff3, fallOff3, fallOff3);

					for (int i = 0; i < 3; i++) {
						if (nVal1.get(i) < 0) {
							nVal1.set(i, 0);
						}
						if (nVal2.get(i) < 0) {
							nVal2.set(i, 0);
						}
						if (nVal3.get(i) < 0) {
							nVal3.set(i, 0);
						}
					}


					for (int ix = -1; ix <= 1; ix++) {
						for (int iy = -1; iy <= 1; iy++) {
							for (int iz = -1; iz <= 1; iz++) {

								int i = 0;

								if (ix != 0) {
									i++;
								}
								if (iy != 0) {
									i++;
								}
								if (iz != 0) {
									i++;
								}

								if (i > 0) {

									poss.add(new Set3i(x + ix, y + iy, z + iz));



									Vector3f nval = null;

									switch (i) {
										case 1:
											nval = nVal1;
											break;
										case 2:
											nval = nVal2;
											break;
										case 3:
											nval = nVal3;
											break;
										default:
											System.out.println("Bad");
									}

									vals.add(nval);
								}
							}
						}
					}


				}



			//} else if (val.length() == 0) {
				//cacheMap.setLight(x, y, z, val);
			//} else if (cacheMap.getLight(x, y, z).length() < 0) {
				//System.out.println("Something went wrong");
			}
		}
	}

	public void delete(World world) {
		for (Set3i pos :cacheMap.getMap().keySet()) {
			world.updateChunk(pos);
		}
	}

	private class TmpMap {

		private final Map<Set3i, LightChunk> map = new HashMap<>();

		private Map<Set3i, LightChunk> getMap() {
			return map;
		}

		private void setLight(int x, int y, int z, Vector3f val) {
			int cx = PositionUtils.getChunk(x, size);
			int cy = PositionUtils.getChunk(y, size);
			int cz = PositionUtils.getChunk(z, size);

			Set3i cPos = new Set3i(cx, cy, cz);

			LightChunk chunk = map.get(cPos);

			if (chunk == null) {
				chunk = new LightChunk(size);
				map.put(cPos, chunk);
			}

			int lx = PositionUtils.getLocal(x, size);
			int ly = PositionUtils.getLocal(y, size);
			int lz = PositionUtils.getLocal(z, size);

			chunk.setLight(lx, ly, lz, val);
		}

		private Vector3f getLight(int x, int y, int z) {
			int cx = PositionUtils.getChunk(x, size);
			int cy = PositionUtils.getChunk(y, size);
			int cz = PositionUtils.getChunk(z, size);

			Set3i cPos = new Set3i(cx, cy, cz);

			LightChunk chunk = map.get(cPos);

			if (chunk == null) {
				chunk = new LightChunk(size);
				map.put(cPos, chunk);
			}

			int lx = PositionUtils.getLocal(x, size);
			int ly = PositionUtils.getLocal(y, size);
			int lz = PositionUtils.getLocal(z, size);

			return chunk.getLight(lx, ly, lz);
		}

		public LightChunk getChunk(Set3i pos) {
			return map.get(pos);
		}
	}

	public class LightChunk {

		private final Vector3f[][][] chunk;

		private LightChunk(int size) {
			this.chunk = new Vector3f[size][size][size];
		}

		private void setLight(int x, int y, int z, Vector3f val) {
			chunk[x][y][z] = val;
		}

		public Vector3f getLight(int x, int y, int z) {
			Vector3f tmpLight = chunk[x][y][z];
			if (tmpLight == null) {
				tmpLight = new Vector3f();
				chunk[x][y][z] = tmpLight;
			}
			return tmpLight;
		}

	}

}
