package com.mthwate.conk

import com.mthwate.conk.info.BlockInfo
import com.mthwate.conk.info.TextureInfo
import com.mthwate.datlib.math.vector.Vector3i

/**
 * @author mthwate
 */
object UpdateUtils {

	fun setBlock(textures: Array<String>, pos: Vector3i) {
		if (textures.size > 0) {

			var textureInfo: TextureInfo? = null

			when (textures.size) {
				1 -> textureInfo = TextureInfo(textures[0])
				2 -> textureInfo = TextureInfo(textures[0], textures[1])
				3 -> textureInfo = TextureInfo(textures[0], textures[1], textures[2])
				6 -> textureInfo = TextureInfo(textures[0], textures[1], textures[2], textures[3], textures[4], textures[5])
			}

			WorldStore.getWorld().setBlock(pos.x, pos.y, pos.z, BlockInfo(textureInfo))
		} else {
			WorldStore.getWorld().setBlock(pos.x, pos.y, pos.z, null)
		}
	}

}
