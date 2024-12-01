package com.demo.springai.rag.runner;

import com.demo.springai.rag.service.RagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartupRunner.class);

    @Value("classpath:pdfs/tr_technology_radar_vol_31_en.pdf")
    private Resource pdf;

    private final RagService ragService;

    public ApplicationStartupRunner(RagService ragService) {
        this.ragService = ragService;
    }

    @Override
    public void run(String... args) throws Exception {
        ragService.textEmbedding(pdf);
        var response = ragService.askLLM("What are some of the most prominent technology trends highlighted in the latest Technology Radar?");
        LOGGER.info("Output: {}", response);
    }
}
