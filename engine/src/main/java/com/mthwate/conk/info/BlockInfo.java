package com.mthwate.conk.info;

/**
 * @author mthwate
 */
public class BlockInfo {

	private final TextureInfo textureInfo;

	private final MaterialInfo materialInfo;

	private final String model;

	public BlockInfo(TextureInfo textureInfo) {
		this(textureInfo, new MaterialInfo(), null);
	}

	public BlockInfo(TextureInfo textureInfo, MaterialInfo materialInfo) {
		this(textureInfo, materialInfo, null);
	}

	public BlockInfo(TextureInfo textureInfo, String model) {
		this(textureInfo, new MaterialInfo(), model);
	}

	public BlockInfo(TextureInfo textureInfo, MaterialInfo materialInfo, String model) {
		this.textureInfo = textureInfo;
		this.materialInfo = materialInfo;
		this.model = model;
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
}
