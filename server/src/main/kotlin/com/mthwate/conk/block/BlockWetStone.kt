package com.mthwate.conk.block

import com.mthwate.conk.command.Exec

/**
 * @author mthwate
 */
@Exec
class BlockWetStone : AbstractBlock("wetStone", "Nature/Underground/Stone/Cavern") {

	override val textures: Array<String>
		get() = arrayOf(path + "1")

}