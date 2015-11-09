package com.mthwate.conk.world;

import com.mthwate.conk.world.generator.RandomWorldGenerator;

/**
 * @author mthwate
 */
public class DimensionStore {

	private static Dimension dim = new Dimension("world", new RandomWorldGenerator());//TODO remove this

	public static Dimension getDimension() {
		return dim;
	}

}
