package com.kokoo.stomp.config

import com.kokoo.stomp.exception.StompErrorHandler
import com.kokoo.stomp.interceptor.StompInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer


@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val stompInterceptor: StompInterceptor,
    private val stompHandshakeHandler: StompHandshakeHandler,
    private val stompErrorHandler: StompErrorHandler
): WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/chat")
            .setHandshakeHandler(stompHandshakeHandler)
            .setAllowedOriginPatterns("*")
            .withSockJS()
            .setHeartbeatTime(3000)

        registry.setErrorHandler(stompErrorHandler)
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setApplicationDestinationPrefixes("/pub")
        registry.enableSimpleBroker("/sub")
            .setTaskScheduler(heartBeatScheduler())
            .setHeartbeatValue(longArrayOf(5000, 5000))
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(stompInterceptor)
    }

    @Bean
    fun heartBeatScheduler(): TaskScheduler {
        return ThreadPoolTaskScheduler()
    }
}