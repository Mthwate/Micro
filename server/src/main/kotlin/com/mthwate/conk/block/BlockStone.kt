package com.mthwate.conk.block

import com.mthwate.conk.command.Exec

/**
 * @author mthwate
 */
@Exec
class BlockStone : AbstractBlock("stone", "Nature/Underground/Stone/Regular") {

	override val textures: Array<String>
		get() = arrayOf(path + "1")

}
