package com.mthwate.conk

import com.mthwate.datlib.math.vector.Vector3i

/**
 * @author mthwate
 */
object PositionUtils {

	fun getLocalFromGlobal(global: Int, chunkSize: Int): Int {
		var n = global

		if (n < 0) {
			n -= chunkSize - 1
		}

		n %= chunkSize

		if (global < 0) {
			n = chunkSize + n - 1
		}

		return n
	}

	fun getLocalFromGlobal(x: Int, y: Int, z: Int, chunkSize: Int): Vector3i {
		val lx = getLocalFromGlobal(x, chunkSize)
		val ly = getLocalFromGlobal(y, chunkSize)
		val lz = getLocalFromGlobal(z, chunkSize)
		return Vector3i(lx, ly, lz)
	}

	fun getLocalFromGlobal(global: Vector3i, chunkSize: Int): Vector3i {
		return getLocalFromGlobal(global.x, global.y, global.z, chunkSize)
	}

	fun getChunkFromGlobal(global: Int, chunkSize: Int): Int {
		var n = global
		if (n < 0) {
			n -= chunkSize - 1
		}

		n /= chunkSize

		return n
	}

	fun getChunkFromGlobal(x: Int, y: Int, z: Int, chunkSize: Int): Vector3i {
		val cx = getChunkFromGlobal(x, chunkSize)
		val cy = getChunkFromGlobal(y, chunkSize)
		val cz = getChunkFromGlobal(z, chunkSize)
		return Vector3i(cx, cy, cz)
	}

	fun getChunkFromGlobal(global: Vector3i, chunkSize: Int): Vector3i {
		return getChunkFromGlobal(global.x, global.y, global.z, chunkSize)
	}

}
