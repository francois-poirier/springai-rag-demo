package com.demo.springai.rag.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Slf4j
public class RagService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Autowired
    public RagService(ChatClient chatClient, VectorStore vectorStore) {
        Assert.notNull(chatClient, "ChatClient must not be null.");
        this.chatClient = chatClient;
        Assert.notNull(vectorStore, "VectorStore must not be null.");
        this.vectorStore = vectorStore;
    }

    public void loadDocument(Resource pdf) {
        /*
        log.info("Saving documents.");

        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdf,
                PdfDocumentReaderConfig.builder()
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfBottomTextLinesToDelete(3)
                                .withNumberOfTopPagesToSkipBeforeDelete(1)
                                .build())
                        .withPagesPerDocument(1)
                        .build());

        var tokenTextSplitter = new TokenTextSplitter();
        this.vectorStore.accept(tokenTextSplitter.apply(pdfReader.get()));
        log.info("Documents saved.");
         */
        // Extract
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdf,
                PdfDocumentReaderConfig.builder()
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfBottomTextLinesToDelete(3)
                                .withNumberOfTopPagesToSkipBeforeDelete(1)
                                .build())
                        .withPagesPerDocument(1)
                        .build());
        // Transform
        var tokenTextSplitter = new TokenTextSplitter();

        log.info(
                "Parsing document, splitting, creating embeddings and storing in vector store...");

        List<Document> splitDocuments = tokenTextSplitter.apply(pdfReader.get());
        // tag as external knowledge in the vector store's metadata
        for (Document splitDocument : splitDocuments) {
            splitDocument.getMetadata().put("filename", pdf.getFilename());
            splitDocument.getMetadata().put("version", 1);
        }

        // Load
        this.vectorStore.accept(splitDocuments);

        log.info("Done parsing document, splitting, creating embeddings and storing in vector store");
  }

    public String askQuestion(String question) {
        return chatClient.prompt().user(question).call().content();
    }

}
