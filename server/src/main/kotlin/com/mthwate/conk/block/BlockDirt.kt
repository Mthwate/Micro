package com.mthwate.conk.block

import com.mthwate.conk.command.Exec

/**
 * @author mthwate
 */
@Exec
class BlockDirt() : BlockBase("dirt") {

	override val textures: Array<String>
		get() = arrayOf(name, name + "Side")

}
