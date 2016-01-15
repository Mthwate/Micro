package com.mthwate.conk.block

/**
 * @author mthwate
 */
abstract class AbstractBlock(override val name: String) : Block {

	override val isSolid = true

	override val isTransparent = false

	override val textures = arrayOf(name)

}
