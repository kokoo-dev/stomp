package com.kokoo.stomp.dto

data class MessageDto(
    var message: String,
    var roomId: Long,
    var userName: String
)
