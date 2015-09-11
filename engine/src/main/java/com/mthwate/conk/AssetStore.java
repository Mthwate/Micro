package com.mthwate.conk;

import com.jme3.asset.*;
import com.jme3.asset.cache.WeakRefCloneAssetCache;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.texture.image.ImageRaster;
import com.jme3.util.BufferUtils;
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

	public static Material getMaterial(AssetManager assetManager, BlockInfo info, Side side, ColorRGBA light) {
		MaterialKey key = new MaterialKey(info.getTextureInfo().getTexture(side) + ":" + info.getMaterialInfo().getMatDef(side) + ":" + info.getMaterialInfo().getTextureType(side) + ":" + info.getTextureInfo().isTransparent() + ":" + light);
		Material material = cache.getFromCache(key);
		if (material == null) {
			material = new Material(assetManager, info.getMaterialInfo().getMatDef(side));
			material.setTexture(info.getMaterialInfo().getTextureType(side), getTexture(assetManager, info, side));

			Image img = new Image();
			img.setFormat(Image.Format.ABGR8);
			img.setWidth(1);
			img.setHeight(1);
			img.setData(BufferUtils.createByteBuffer((int) Math.ceil(Image.Format.ABGR8.getBitsPerPixel() / 8)));
			ImageRaster.create(img).setPixel(0, 0, light);
			Texture2D t = new Texture2D(img);
			material.setTexture("LightMap", t);

			if (info.getTextureInfo().isTransparent()) {
				material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
			}
			cache.addToCache(key, material);
			material = cache.getFromCache(key);
		}
		return material;
	}

	public static Spatial getSpatial(AssetManager assetManager, BlockInfo info, Side side, ColorRGBA light) {
		AssetKey<Spatial> key = new AssetKey<>(info.getTextureInfo().getTexture(side) + ":" + info.getModel() + ":" + info.getMaterialInfo().getMatDef(side) + ":" + info.getMaterialInfo().getTextureType(side) + ":" + light);
		Spatial spatial = cache.getFromCache(key);
		if (spatial == null) {
			if (info.getModel() == null) {
				spatial = new Geometry(null, QUAD);
			} else {
				spatial = assetManager.loadModel(new ModelKey(info.getModel() + ".blend"));
			}
			spatial.setMaterial(AssetStore.getMaterial(assetManager, info, side, light));
			cache.addToCache(key, spatial);
			spatial = cache.getFromCache(key);
		}
		return spatial.clone(false);
	}

}
