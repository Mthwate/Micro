package com.mthwate.conk.block

/**
 * @author mthwate
 */
interface Block {

	val name: String

	val isSolid: Boolean

	val isTransparent: Boolean

	val textures: Array<String>

}
