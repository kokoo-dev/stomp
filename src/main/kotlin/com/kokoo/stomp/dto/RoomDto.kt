package com.kokoo.stomp.dto

import com.kokoo.stomp.constant.UserAction

data class RoomDto(
    var roomId: Long = 0,
    var userName: String = "",
    var userAction: UserAction = UserAction.ENTRANCE
)