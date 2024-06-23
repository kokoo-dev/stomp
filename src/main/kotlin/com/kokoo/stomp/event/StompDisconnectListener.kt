package com.kokoo.stomp.event

import com.kokoo.stomp.service.ChatService
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Configuration
class StompDisconnectListener(
    private val chatService: ChatService
) {

    @EventListener
    fun onEvent(event: SessionDisconnectEvent) {
        chatService.leaveRoom(event.sessionId)
    }
}