package com.kokoo.stomp.exception

import com.kokoo.stomp.constant.StompStatus
import org.springframework.messaging.MessageDeliveryException

open class SocketCustomException(
    override val message: String,
    val code: String,
    val sessionId: String?
) : MessageDeliveryException(message) {
    constructor(stompStatus: StompStatus, sessionId: String?) : this(stompStatus.code, stompStatus.message, sessionId)
}