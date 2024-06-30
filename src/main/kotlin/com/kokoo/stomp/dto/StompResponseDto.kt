package com.kokoo.stomp.dto

import com.kokoo.stomp.constant.StompStatus

class StompResponseDto(
    val code: String = StompStatus.OK.code,
    val message: String = StompStatus.OK.message,
) {

    companion object {
        fun of(stompStatus: StompStatus): StompResponseDto {
            return StompResponseDto(code = stompStatus.code, message = stompStatus.message)
        }

        fun of(code: String, message: String): StompResponseDto {
            return StompResponseDto(code = code, message = message)
        }
    }
}