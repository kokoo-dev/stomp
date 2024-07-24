package com.kokoo.stomp.service

import com.kokoo.stomp.constant.StompStatus
import com.kokoo.stomp.constant.UserAction
import com.kokoo.stomp.domain.Room
import com.kokoo.stomp.domain.RoomUser
import com.kokoo.stomp.dto.MessageDto
import com.kokoo.stomp.dto.RoomDto
import com.kokoo.stomp.exception.SocketCustomException
import com.kokoo.stomp.repository.RoomRepository
import com.kokoo.stomp.repository.RoomUserRepository
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatService(
    private val simpMessageTemplate: SimpMessagingTemplate,

    private val roomRepository: RoomRepository,
    private val roomUserRepository: RoomUserRepository
) {

    @Transactional
    fun enterRoom(accessor: SimpMessageHeaderAccessor, roomDto: RoomDto) {
        val roomId = roomDto.roomId
        if (roomRepository.findById(roomId).isEmpty) {
            roomRepository.save(Room(id = roomId))
        }

        val userName = roomDto.userName
        // exception not working, interceptor에서 처리
//        roomUserRepository.findByUserName(userName)?.run {
//            throw SocketCustomException(StompStatus.ALREADY_EXISTS, accessor.sessionId)
//        }

        roomUserRepository.save(RoomUser(roomId = roomId, userName = userName, sessionId = accessor.sessionId.orEmpty()))

        simpMessageTemplate.convertAndSend("/sub/room/${roomId}", roomDto)
    }

    @Transactional
    fun leaveRoom(sessionId: String) {
        val roomUser = roomUserRepository.findBySessionId(sessionId) ?: return // TODO throw
        val roomDto = RoomDto(
            roomId = roomUser.roomId,
            userName = roomUser.userName,
            userAction = UserAction.EXIT
        )

        roomUserRepository.deleteById(roomUser.id)

        simpMessageTemplate.convertAndSend("/sub/room/${roomUser.roomId}", roomDto)
    }

    fun sendMessage(messageDto: MessageDto) {
        simpMessageTemplate.convertAndSend("/sub/room/${messageDto.roomId}/chat", messageDto)
    }
}