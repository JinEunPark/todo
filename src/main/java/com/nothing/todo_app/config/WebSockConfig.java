package com.nothing.todo_app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
//https://learnote-dev.com/java/Spring-%EA%B2%8C%EC%8B%9C%ED%8C%90-API-%EB%A7%8C%EB%93%A4%EA%B8%B0-webSocket%EC%9C%BC%EB%A1%9C-%EC%B1%84%ED%8C%85%EC%84%9C%EB%B2%84-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0/
//open source url you have to care about it
public class WebSockConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "ws/chat").setAllowedOrigins("*");
    }
}
