package com.mthwate.conk.block

import com.mthwate.conk.command.Exec

/**
 * @author mthwate
 */
@Exec
class BlockStone : AbstractBlock("stone") {

	override val textures = arrayOf("${name}1")

}
