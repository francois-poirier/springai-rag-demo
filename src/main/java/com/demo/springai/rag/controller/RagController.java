package com.demo.springai.rag.controller;

import com.demo.springai.rag.service.RagService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/rag")
    public String rag(@RequestParam(name = "query") String query) {
        return ragService.askLLM(query);
    }
}
