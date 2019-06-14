package com.heeexy.example.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.heeexy.example.controller.MyHandler;

//@Configuration
//@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "myHandler").addInterceptors(new HandshakeInterceptor()).setAllowedOrigins("*");
    }
    public WebSocketHandler myHandler() {
        return new MyHandler();
    }

}