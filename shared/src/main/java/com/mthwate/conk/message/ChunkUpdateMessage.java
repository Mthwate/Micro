package com.mthwate.conk.message;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.mthwate.datlib.math.vector.Vector3i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mthwate
 */
@Serializable
public class ChunkUpdateMessage extends AbstractMessage {

	private int startX;
	private int startY;
	private int startZ;

	private int sizeX;
	private int sizeY;
	private int sizeZ;

	private int[] pos;

	private List<String[]> textures = new ArrayList<>();

	public ChunkUpdateMessage() {}

	public ChunkUpdateMessage(String[][][][] texturesMap, Vector3i start, Vector3i size) {
		startX = start.getX();
		startY = start.getY();
		startZ = start.getZ();

		sizeX = size.getX();
		sizeY = size.getY();
		sizeZ = size.getZ();

		List<String[]> list = new ArrayList<String[]>() {

			@Override
			public boolean add(String[] e) {
				if (!this.contains(e)) {
					return super.add(e);
				}
				return false;
			}

			@Override
			public int indexOf(Object o) {
				if (o instanceof String[]) {
					if (o == null) {
						for (int i = 0; i < size(); i++) {
							if (get(i) == null) {
								return i;
							}
						}
					} else {
						for (int i = 0; i < size(); i++) {
							if (Arrays.equals((String[]) o, get(i))) {
								return i;
							}
						}
					}
				}
				return -1;
			}

		};

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				for (int z = 0; z < sizeZ; z++) {
					list.add(texturesMap[x][y][z]);
				}
			}
		}

		for (String[] strArray : list) {
			textures.add(strArray);
		}

		pos = new int[sizeX * sizeY * sizeZ];

		int i = 0;

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				for (int z = 0; z < sizeZ; z++) {
					pos[i++] = indexOf(texturesMap[x][y][z]);
				}
			}
		}
	}

	private int indexOf(String[] strArray) {
		for (int i = 0; i < textures.size(); i++) {
			if (Arrays.equals(textures.get(i), strArray)) {
				return i;
			}
		}
		return -1;
	}

	public String[][][][] getTextures() {
		String[][][][] texturesMap = new String[sizeX][sizeY][sizeZ][];

		int i = 0;

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				for (int z = 0; z < sizeZ; z++) {
					String[] array = textures.get(pos[i++]);
					texturesMap[x][y][z] = Arrays.copyOf(array, array.length);
				}
			}
		}

		return texturesMap;
	}

	public Vector3i getSize() {
		return new Vector3i(sizeX, sizeY, sizeZ);
	}

	public Vector3i getStart() {
		return new Vector3i(startX, startY, startZ);
	}

}