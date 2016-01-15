package com.mthwate.conk.command

import com.mthwate.conk.ServerApp

/**
 * @author mthwate
 */
@Exec
class CommandStop : Command {

	override val name = "stop"

	override fun run(app: ServerApp, args: Array<String>) = app.stop()

}
