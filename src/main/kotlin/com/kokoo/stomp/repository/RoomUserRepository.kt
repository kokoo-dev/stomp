package com.kokoo.stomp.repository

import com.kokoo.stomp.domain.RoomUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoomUserRepository : JpaRepository<RoomUser, Long> {
    fun findByUserName(userName: String): RoomUser?
    fun findBySessionId(sessionId: String): RoomUser?
}