package com.kokoo.stomp.controller

import com.kokoo.stomp.dto.MessageDto
import com.kokoo.stomp.dto.RoomDto
import com.kokoo.stomp.service.ChatService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller

@Controller
@MessageMapping("/room")
class SocketController(
    private val chatService: ChatService
) {

    @MessageMapping("")
    fun enterRoom(simpMessageHeaderAccessor: SimpMessageHeaderAccessor, roomDto: RoomDto) {
        chatService.enterRoom(roomDto)
    }

    @MessageMapping("/chat")
    fun sendMessage(simpMessageHeaderAccessor: SimpMessageHeaderAccessor, messageDto: MessageDto) {
        chatService.sendMessage(messageDto)
    }
}