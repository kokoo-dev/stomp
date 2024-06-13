package com.kokoo.stomp.config

import com.kokoo.stomp.interceptor.StompInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val stompInterceptor: StompInterceptor,
): WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // TODO Handshake Handler, Error Handler
        registry.addEndpoint("/chat")
            .setAllowedOriginPatterns("*")
            .withSockJS()
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setApplicationDestinationPrefixes("/pub")
        registry.enableSimpleBroker("/sub")
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(stompInterceptor)
    }
}