package com.example.musicplaylist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketHandshakeInterceptor handshakeInterceptor;

    public WebSocketConfig(WebSocketHandshakeInterceptor handshakeInterceptor) {
        this.handshakeInterceptor = handshakeInterceptor;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); // 클라이언트가 구독할 주소
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 보낼 때 접두사
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("http://localhost:8080", "https://playlist.abz.kr").addInterceptors(handshakeInterceptor).withSockJS(); // WebSocket 연결 엔드포인트
    }
}
