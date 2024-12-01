package com.demo.springai.rag.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RagService.class);
    private static final String PROMPT_BLUEPRINT = """
        Answer the query strictly referring the provided context:
        {context}
        Query:
        {query}
        In case you don't have any answer from the context provided, just say:
        I'm sorry I don't have the information you are looking for.
    """;
    private final ChatModel chatClient;
    private final VectorStore vectorStore;

    public RagService(ChatModel chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    public void textEmbedding(Resource pdf) {
        LOGGER.info("Saving documents.");
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
        LOGGER.info("Documents saved.");
    }

    public String askLLM(String query) {
        return chatClient.call(createPrompt(query, vectorStore.similaritySearch(query)));
    }

    private String createPrompt(String query, List<Document> context) {
        PromptTemplate promptTemplate = new PromptTemplate(PROMPT_BLUEPRINT);
        promptTemplate.add("query", query);
        promptTemplate.add("context", context);
        return promptTemplate.render();
    }
}
