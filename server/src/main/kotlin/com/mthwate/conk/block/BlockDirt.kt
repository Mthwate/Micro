package com.mthwate.conk.block

import com.mthwate.conk.command.Exec

/**
 * @author mthwate
 */
@Exec
class BlockDirt() : AbstractBlock("topsoil", "Nature/Surface/Dirt") {

	override val textures: Array<String>
		get() = arrayOf(path, path + "Side")

}
