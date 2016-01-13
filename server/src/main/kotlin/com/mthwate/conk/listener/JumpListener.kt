package com.mthwate.conk.listener

import com.mthwate.conk.message.JumpMessage
import com.mthwate.conk.user.UserStore

/**
 * @author mthwate
 */
class JumpListener : LazyServerListener<JumpMessage>({ src, msg -> UserStore.getUser(src)?.control?.jump() })