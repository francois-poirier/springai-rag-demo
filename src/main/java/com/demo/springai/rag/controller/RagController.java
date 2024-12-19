package com.demo.springai.rag.controller;

import com.demo.springai.rag.service.RagService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("fileToUpload") MultipartFile document) {
        ragService.loadDocument(document.getResource());

        return "redirect:/chat";
    }

    @GetMapping("/chat")
    public String loadChat() {
        return "chat";
    }
}
