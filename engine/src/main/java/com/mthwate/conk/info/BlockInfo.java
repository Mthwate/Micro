package com.mthwate.conk.info;

import com.jme3.math.Vector3f;

/**
 * @author mthwate
 */
public class BlockInfo {

	private final TextureInfo textureInfo;

	private final MaterialInfo materialInfo;

	private final String model;

	/**
	 * The ammount of light this block emits.
	 */
	private final Vector3f light;

	private final boolean solid;

	public BlockInfo(TextureInfo textureInfo) {
		this(textureInfo, new MaterialInfo(), null, null, true);
	}

	public BlockInfo(TextureInfo textureInfo, Vector3f light) {
		this(textureInfo, new MaterialInfo(), null, light, true);
	}

	public BlockInfo(TextureInfo textureInfo, MaterialInfo materialInfo) {
		this(textureInfo, materialInfo, null, null, true);
	}

	public BlockInfo(TextureInfo textureInfo, String model) {
		this(textureInfo, new MaterialInfo(), model, null, true);
	}

	public BlockInfo(TextureInfo textureInfo, MaterialInfo materialInfo, String model, Vector3f light, boolean solid) {
		this.textureInfo = textureInfo;
		this.materialInfo = materialInfo;
		this.model = model;
		this.light = light;
		this.solid = solid;
	}

	public TextureInfo getTextureInfo() {
		return textureInfo;
	}

	public String getModel() {
		return model;
	}

	public MaterialInfo getMaterialInfo() {
		return materialInfo;
	}

	public Vector3f getLight() {
		return light;
	}

	public float getLightFalloff() {
		return 0.05f;
	}

	public boolean isSolid() {
		return solid;
	}
}
