package com.mthwate.conk.block

import com.mthwate.conk.command.Exec

/**
 * @author mthwate
 */
@Exec
class BlockAir : BlockBase("air") {

	override val isSolid: Boolean
		get() = false

	override val isTransparent: Boolean
		get() = true

	override val textures: Array<String>
		get() = arrayOf()

}
