package com.kokoo.stomp.interceptor

import com.kokoo.stomp.service.ChatService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor

@Configuration
class StompInterceptor : ChannelInterceptor {

    private val log = KotlinLogging.logger {}

    override fun postSend(message: Message<*>, channel: MessageChannel, sent: Boolean) {
        val accessor: StompHeaderAccessor = StompHeaderAccessor.wrap(message)

        log.info { "Message Type :: ${accessor.messageType}" }

        when (accessor.messageType) {
            SimpMessageType.CONNECT -> {

            }

            SimpMessageType.DISCONNECT -> {

            }

            SimpMessageType.HEARTBEAT -> {

            }

            SimpMessageType.OTHER -> {

            }

            else -> {

            }
        }
    }
}