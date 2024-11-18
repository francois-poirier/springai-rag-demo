package com.demo.springai.rag.service;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RagService {

    private final VectorStore vectorStore;

    private final JdbcTemplate jdbcTemplate;

    private final ChatModel chatModel;;

    public RagService(VectorStore vectorStore, JdbcTemplate jdbcTemplate, OpenAiChatModel chatModel) {
        this.vectorStore = vectorStore;
        this.jdbcTemplate = jdbcTemplate;
        this.chatModel = chatModel;
    }

    public String askLLM(String query) {
        SearchRequest searchRequest = SearchRequest.query(query).withTopK(3);
        List<Document> documentList = vectorStore.similaritySearch(searchRequest);

        String systemMessageTemplate =
                """
                Answer the following question based only in the provided CONTEXT
                If the answer is not found respond : "I don't know".
                CONTEXT :
                    {CONTEXT}
                """;
        Message systemMessage = new SystemPromptTemplate(systemMessageTemplate)
                .createMessage(Map.of("CONTEXT", documentList));
        UserMessage userMessage = new UserMessage(query);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = chatModel.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    public void textEmbedding(Resource[] pdfs) {
        jdbcTemplate.update("DELETE FROM vector_store");
        PdfDocumentReaderConfig pdfDocumentReaderConfig = PdfDocumentReaderConfig.defaultConfig();
        List<Document> documents = List.of();
        for (Resource pdf : pdfs) {
            PagePdfDocumentReader pdfDocumentReader =
                    new PagePdfDocumentReader(pdf, pdfDocumentReaderConfig);
            documents = pdfDocumentReader.get();
        }
        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
        List<Document> chunks = tokenTextSplitter.split(documents);
        vectorStore.accept(chunks);
    }
}
