package com.mthwate.conk.block

import com.mthwate.conk.command.Exec

/**
 * @author mthwate
 */
@Exec
class BlockAir : AbstractBlock("air") {

	override val isSolid = false

	override val isTransparent = true

	override val textures: Array<String> = arrayOf()

}
