package com.kokoo.stomp.domain

import jakarta.persistence.*

@Entity
data class Room(
    @Id
    var id: Long = 0,
) {
    @OneToMany(mappedBy = "room", targetEntity = RoomUser::class)
    var roomUsers: List<RoomUser> = emptyList()
}