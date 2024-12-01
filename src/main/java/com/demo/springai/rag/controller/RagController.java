package com.demo.springai.rag.controller;

import com.demo.springai.rag.service.RagService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/rag")
    public Map<String,String> chat(@RequestParam(name = "query") String query) {
        return Map.of("answer", ragService.askLLM(query));
    }
}
