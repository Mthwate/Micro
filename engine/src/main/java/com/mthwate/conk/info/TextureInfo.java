package com.mthwate.conk.info;

import com.mthwate.conk.Side;

/**
 * @author mthwate
 */
public class TextureInfo {

	private final String[] textures;

	private final short[] indexes = new short[6];

	private final boolean transparent;

	public TextureInfo(String texture) {
		this(texture, false);
	}

	public TextureInfo(String texture, boolean transparent) {
		textures = new String[] {texture};
		for (int i = 0; i < 6; i++) {
			indexes[i] = 0;
		}
		this.transparent = transparent;
	}

	public TextureInfo(String textureTopBottom, String textureSide) {
		this(textureTopBottom, textureSide, false);
	}

	public TextureInfo(String textureTopBottom, String textureSide, boolean transparent) {
		textures = new String[] {textureTopBottom, textureSide};
		for (int i = 0; i < 6; i++) {
			indexes[i] = (short) (i < 2 ? 0 : 1);
		}
		this.transparent = transparent;
	}

	public TextureInfo(String textureTop, String textureBottom, String textureSide) {
		this(textureTop, textureBottom, textureSide, false);
	}

	public TextureInfo(String textureTop, String textureBottom, String textureSide, boolean transparent) {
		textures = new String[] {textureTop, textureBottom, textureSide};
		for (int i = 0; i < 6; i++) {
			indexes[i] = (short) Math.min(i, 2);
		}
		this.transparent = transparent;
	}

	public TextureInfo(String textureTop, String textureBottom, String textureFront, String textureBack, String textureLeft, String textureRight) {
		this(textureTop, textureBottom, textureFront, textureBack, textureLeft, textureRight, false);
	}

	public TextureInfo(String textureTop, String textureBottom, String textureFront, String textureBack, String textureLeft, String textureRight, boolean transparent) {
		textures = new String[] {textureTop, textureBottom, textureFront, textureBack, textureLeft, textureRight};
		for (short i = 0; i < 6; i++) {
			indexes[i] = i;
		}
		this.transparent = transparent;
	}

	public String getTexture(Side side) {
		return textures[indexes[side.getI()]];
	}

	public boolean isTransparent() {
		return transparent;
	}

}
