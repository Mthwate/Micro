package com.mthwate.conk.listener

import com.mthwate.conk.block.BlockAir
import com.mthwate.conk.message.BlockBreakMessage
import com.mthwate.conk.world.DimensionStore

/**
 * @author mthwate
 */
class BlockBreakListener : LazyServerListener<BlockBreakMessage>({ src, msg -> DimensionStore.getDimension().setBlock(BlockAir(), msg.pos) })