package com.demo.springai.rag.config;

import com.demo.springai.rag.service.RagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private RagService ragService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(
                new TextWebSocketHandler() {
                    @Override
                    public void handleTextMessage(WebSocketSession session, TextMessage message)
                            throws IOException {
                        String response = ragService.askQuestion(message.getPayload());
                        session.sendMessage(new TextMessage(response));
                    }
                },
                "/ws-chat");
    }
}
