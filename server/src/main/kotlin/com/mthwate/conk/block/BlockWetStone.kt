package com.mthwate.conk.block

import com.mthwate.conk.command.Exec

/**
 * @author mthwate
 */
@Exec
class BlockWetStone : AbstractBlock("wetStone") {

	override val textures = arrayOf("${name}1")

}