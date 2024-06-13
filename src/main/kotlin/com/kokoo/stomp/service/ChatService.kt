package com.kokoo.stomp.service

import com.kokoo.stomp.dto.MessageDto
import com.kokoo.stomp.dto.RoomDto
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val simpMessageTemplate: SimpMessagingTemplate
) {

    fun enterRoom(roomDto: RoomDto) {
        simpMessageTemplate.convertAndSend("/sub/room/${roomDto.roomId}", roomDto)
    }

    fun sendMessage(messageDto: MessageDto) {
        simpMessageTemplate.convertAndSend("/sub/room/${messageDto.roomId}/chat", messageDto)
    }
}