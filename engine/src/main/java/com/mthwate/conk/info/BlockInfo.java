package com.mthwate.conk.info;

import com.jme3.math.Vector3f;

/**
 * @author mthwate
 */
public class BlockInfo {

	private final TextureInfo textureInfo;

	private final MaterialInfo materialInfo;

	private final String model;

	private final Vector3f light;

	public BlockInfo(TextureInfo textureInfo) {
		this(textureInfo, new MaterialInfo(), null, null);
	}

	public BlockInfo(TextureInfo textureInfo, Vector3f light) {
		this(textureInfo, new MaterialInfo(), null, light);
	}

	public BlockInfo(TextureInfo textureInfo, MaterialInfo materialInfo) {
		this(textureInfo, materialInfo, null, null);
	}

	public BlockInfo(TextureInfo textureInfo, String model) {
		this(textureInfo, new MaterialInfo(), model, null);
	}

	public BlockInfo(TextureInfo textureInfo, MaterialInfo materialInfo, String model, Vector3f light) {
		this.textureInfo = textureInfo;
		this.materialInfo = materialInfo;
		this.model = model;
		this.light = light;
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
		return 0.1f;
	}
}
