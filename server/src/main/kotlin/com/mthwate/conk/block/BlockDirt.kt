package com.mthwate.conk.block

import com.mthwate.conk.command.Exec

/**
 * @author mthwate
 */
@Exec
class BlockDirt() : AbstractBlock("topsoil") {

	override val textures: Array<String>
		get() = arrayOf(name, name + "Side")

}
