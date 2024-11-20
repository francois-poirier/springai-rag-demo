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
                                        @Value("classpath:pdfs/tr_technology_radar_vol_31_en.pdf") Resource pdf) {
        return args -> {
            ragService.textEmbedding(pdf);
            ragService.askLLM("What are some of the most prominent technology trends highlighted in the latest Technology Radar?");
        };
    }


}
