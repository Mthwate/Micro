package com.mthwate.conk;

/**
 * @author mthwate
 */
public enum Side {

	TOP(0),
	BOTTOM(1),
	FRONT(2),
	BACK(3),
	LEFT(4),
	RIGHT(5);

	private int i;

	Side(int i) {
		this.i = i;
	}

	public int getI() {
		return i;
	}

	private static Side[] sides = new Side[6];

	static {
		setSide(TOP);
		setSide(BOTTOM);
		setSide(FRONT);
		setSide(BACK);
		setSide(LEFT);
		setSide(RIGHT);
	}

	private static void setSide(Side side) {
		sides[side.getI()] = side;
	}

	public static Side getSide(int i) {
		return sides[i];
	}

}
