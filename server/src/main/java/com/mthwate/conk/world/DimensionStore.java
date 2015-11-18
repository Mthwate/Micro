package com.mthwate.conk.world;

import com.mthwate.conk.world.generator.RandFlatWorldGenerator;

/**
 * @author mthwate
 */
public class DimensionStore {

	private static Dimension dim = new Dimension("world", new RandFlatWorldGenerator());//TODO remove this

	public static Dimension getDimension() {
		return dim;
	}

}
