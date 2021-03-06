package com.mthwate.conk.info;

import com.mthwate.conk.Side;

/**
 * @author mthwate
 */
public class MaterialInfo {

	private final String matDef;

	private final String textureType;

	public MaterialInfo() {
		this("Common/MatDefs/Misc/Unshaded.j3md", "ColorMap");
	}

	public MaterialInfo(String matDef, String textureType) {
		this.matDef = matDef;
		this.textureType = textureType;
	}

	public String getMatDef(Side side) {
		return matDef;
	}

	public String getTextureType(Side side) {
		return textureType;
	}

}
