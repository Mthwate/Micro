package com.mthwate.conk;

import com.jme3.asset.*;
import com.jme3.asset.cache.WeakRefCloneAssetCache;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.mthwate.conk.info.BlockInfo;

/**
 * @author mthwate
 */
public class AssetStore {

	private static final WeakRefCloneAssetCache cache = new WeakRefCloneAssetCache();

	private static final Quad QUAD = new Quad(1, 1);

	public static Texture getTexture(AssetManager assetManager, BlockInfo info, Side side) {
		TextureKey key = new TextureKey(info.getTextureInfo().getTexture(side) + ".png");
		Texture texture = cache.getFromCache(key);
		if (texture == null) {
			texture = assetManager.loadAsset(key);
			texture.setMagFilter(Texture.MagFilter.Nearest);
			cache.addToCache(key, texture);
			texture = cache.getFromCache(key);
		}
		return texture;
	}

	public static Material getMaterial(AssetManager assetManager, BlockInfo info, Side side) {
		MaterialKey key = new MaterialKey(info.getTextureInfo().getTexture(side) + ":" + info.getMaterialInfo().getMatDef(side) + ":" + info.getMaterialInfo().getTextureType(side) + "" + info.getTextureInfo().isTransparent());
		Material material = cache.getFromCache(key);
		if (material == null) {
			material = new Material(assetManager, info.getMaterialInfo().getMatDef(side));
			material.setTexture(info.getMaterialInfo().getTextureType(side), getTexture(assetManager, info, side));
			if (info.getTextureInfo().isTransparent()) {
				material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
			}
			cache.addToCache(key, material);
			material = cache.getFromCache(key);
		}
		return material;
	}

	public static Spatial getSpatial(AssetManager assetManager, BlockInfo info, Side side) {
		AssetKey<Spatial> key = new AssetKey<>(info.getTextureInfo().getTexture(side) + ":" + info.getModel() + ":" + info.getMaterialInfo().getMatDef(side) + ":" + info.getMaterialInfo().getTextureType(side));
		Spatial spatial = cache.getFromCache(key);
		if (spatial == null) {
			if (info.getModel() == null) {
				spatial = new Geometry(null, QUAD);
			} else {
				spatial = assetManager.loadModel(new ModelKey(info.getModel() + ".blend"));
			}
			spatial.setMaterial(AssetStore.getMaterial(assetManager, info, side));
			cache.addToCache(key, spatial);
			spatial = cache.getFromCache(key);
		}
		return spatial.clone(false);
	}

}
