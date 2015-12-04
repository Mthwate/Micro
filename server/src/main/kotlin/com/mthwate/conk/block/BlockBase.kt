package com.mthwate.conk.block

/**
 * @author mthwate
 */
abstract class BlockBase(override val name: String) : Block {

	override val isSolid: Boolean
		get() = true

	override val isTransparent: Boolean
		get() = false

	override val textures: Array<String>
		get() = arrayOf(name)

}
