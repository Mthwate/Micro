package com.mthwate.conk;

import com.mthwate.conk.info.BlockInfo;
import com.mthwate.conk.info.TextureInfo;
import com.mthwate.datlib.math.vector.Vector3i;

/**
 * @author mthwate
 */
public class UpdateUtils {

	public static void setBlock(String[] textures, Vector3i pos) {
		if (textures.length > 0) {

			TextureInfo textureInfo = null;

			switch (textures.length) {
				case 1:
					textureInfo = new TextureInfo(textures[0]);
					break;
				case 2:
					textureInfo = new TextureInfo(textures[0], textures[1]);
					break;
				case 3:
					textureInfo = new TextureInfo(textures[0], textures[1], textures[2]);
					break;
				case 6:
					textureInfo = new TextureInfo(textures[0], textures[1], textures[2], textures[3], textures[4], textures[5]);
					break;
			}

			WorldStore.getWorld().setBlock(pos.getX(), pos.getY(), pos.getZ(), new BlockInfo(textureInfo));
		} else {
			WorldStore.getWorld().setBlock(pos.getX(), pos.getY(), pos.getZ(), null);
		}
	}

}
