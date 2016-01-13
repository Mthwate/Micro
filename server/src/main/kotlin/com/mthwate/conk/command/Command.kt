package com.mthwate.conk.command

import com.mthwate.conk.ServerApp

/**
 * @author mthwate
 */
interface Command {

	val name: String

	fun run(app: ServerApp, args: Array<String>)

}
