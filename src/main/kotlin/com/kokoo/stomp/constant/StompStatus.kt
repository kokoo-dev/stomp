package com.kokoo.stomp.constant

enum class StompStatus(
    val code: String,
    val message: String
) {
    OK("OK", "성공"),
    SERVER_ERROR("SERVER_ERROR", "서버 오류"),
    ALREADY_EXISTS("ALREADY_EXISTS", "이미 존재합니다."),
}