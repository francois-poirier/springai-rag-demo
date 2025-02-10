package com.demo.springai.rag.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ChatClientConfig {

    @Bean
    @Profile("prod")
    public ChatClient openAIChatClient(OpenAiChatModel chatModel, VectorStore vectorStore) {
        return ChatClient.builder(chatModel)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new QuestionAnswerAdvisor(
                                vectorStore, SearchRequest.builder().topK(SearchRequest.DEFAULT_TOP_K).similarityThreshold(SearchRequest.SIMILARITY_THRESHOLD_ACCEPT_ALL).build()) // RAG advisor
                )
                .build();
    }
    @Bean
    @Profile("dev")
    public ChatClient ollamaChatClient(OllamaChatModel chatModel, VectorStore vectorStore) {
        return ChatClient.builder(chatModel)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new QuestionAnswerAdvisor(
                                vectorStore, SearchRequest.builder().topK(SearchRequest.DEFAULT_TOP_K).similarityThreshold(SearchRequest.SIMILARITY_THRESHOLD_ACCEPT_ALL).build()) // RAG advisor
                        )
                .build();
    }
}
