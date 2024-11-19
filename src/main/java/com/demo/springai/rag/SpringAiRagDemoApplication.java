package com.demo.springai.rag;

import com.demo.springai.rag.service.RagService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class SpringAiRagDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiRagDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(RagService ragService,
                                        @Value("classpath:pdfs/*") Resource[] pdfs) {
        return args -> {
            ragService.textEmbedding(pdfs);
            ragService.askLLM("What are the key new features and improvements introduced in Spring Boot 3, and how do they enhance the development experience for Java applications?");
        };
    }


}
