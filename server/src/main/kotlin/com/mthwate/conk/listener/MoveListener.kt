package com.mthwate.conk.listener

import com.jme3.math.Vector3f
import com.mthwate.conk.message.MoveMessage
import com.mthwate.conk.user.UserStore

/**
 * @author mthwate
 */
class MoveListener : LazyServerListener<MoveMessage>({ src, msg -> UserStore.getUser(src)?.control?.walkDirection = Vector3f(msg.x, 0f, msg.z) })