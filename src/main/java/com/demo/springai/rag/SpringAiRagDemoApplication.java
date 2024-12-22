package com.demo.springai.rag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
public class SpringAiRagDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiRagDemoApplication.class, args);
    }
}
