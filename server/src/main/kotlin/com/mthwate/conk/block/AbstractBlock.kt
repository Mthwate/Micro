package com.mthwate.conk.block

/**
 * @author mthwate
 */
abstract class AbstractBlock(override val name: String, private val directory: String = "") : Block {

	val path: String
		get() = directory + '/' + name;

	override val isSolid: Boolean
		get() = true

	override val isTransparent: Boolean
		get() = false

	override val textures: Array<String>
		get() = arrayOf(path)

}
