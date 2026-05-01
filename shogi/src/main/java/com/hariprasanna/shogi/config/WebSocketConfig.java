package com.hariprasanna.shogi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // This is the URL React will use to connect: ws://localhost:8080/shogi-websocket
        registry.addEndpoint("/shogi-websocket")
                .setAllowedOriginPatterns("*"); // Allow React to connect
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // The server will broadcast messages to any channel starting with "/topic"
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
}