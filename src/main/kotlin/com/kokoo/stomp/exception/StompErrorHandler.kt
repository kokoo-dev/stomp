package com.kokoo.stomp.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.kokoo.stomp.dto.StompResponseDto
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler
import java.nio.charset.StandardCharsets

@Configuration
class StompErrorHandler(
    private val objectMapper: ObjectMapper
) : StompSubProtocolErrorHandler() {

    private val log = KotlinLogging.logger {}

    override fun handleClientMessageProcessingError(
        clientMessage: Message<ByteArray>?,
        ex: Throwable
    ): Message<ByteArray>? {
        if (ex is SocketCustomException) {
            val exception: SocketCustomException = ex

            log.error(ex) { "Socket Custom Exception:: session id: ${exception.sessionId} " }

            return createMessage(
                objectMapper.writeValueAsString(
                    StompResponseDto.of(
                        code = exception.code,
                        message = exception.message
                    )
                )
            )
        }

        log.error(ex) { "Stomp Error Handler" }

        return super.handleClientMessageProcessingError(clientMessage, ex)
    }

    private fun createMessage(errorMessage: String): Message<ByteArray> {
        val accessor = StompHeaderAccessor.create(StompCommand.ERROR)
        accessor.setLeaveMutable(true)

        return MessageBuilder.createMessage(
            errorMessage.toByteArray(StandardCharsets.UTF_8),
            accessor.messageHeaders
        )
    }
}