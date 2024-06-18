package com.kokoo.stomp.domain

import jakarta.persistence.*

@Entity
data class RoomUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column
    var roomId: Long = 0,

    @Column
    var userName: String = "",

    @Column
    var sessionId: String = ""
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId", insertable = false, updatable = false)
    var room: Room? = null
}