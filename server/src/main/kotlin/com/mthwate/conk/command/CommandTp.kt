package com.mthwate.conk.command

import com.jme3.math.Vector3f
import com.mthwate.conk.ServerApp
import com.mthwate.conk.user.UserStore

/**
 * @author mthwate
 */
@Exec
class CommandTp : Command {

	override val name = "tp"

	override fun run(app: ServerApp, args: Array<String>) {
		val src = UserStore.getUser(args[0])

		var pos: Vector3f? = null

		if (args.size == 2) {
			pos = UserStore.getUser(args[1])?.position
		} else if (args.size == 4) {
			val x = Integer.parseInt(args[1])
			val y = Integer.parseInt(args[2])
			val z = Integer.parseInt(args[3])
			pos = Vector3f(x.toFloat(), y.toFloat(), z.toFloat())
		}

		if (src != null && pos != null) {
			src.control.warp(pos)
		}
	}

}
