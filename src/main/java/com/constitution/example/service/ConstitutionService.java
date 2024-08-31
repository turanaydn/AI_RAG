package com.constitution.example.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConstitutionService {


    private final ChatModel chatModel;
    private final VectorStore vectorStore;

    public ConstitutionService(ChatModel chatModel, VectorStore vectorStore) {
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
    }

    private String  prompt = """
            Your task is to answer the questions about Turkey Constitution. Use the information from the DOCUMENTS
            section to provide accurate answers. If unsure or if the answer isn't found in the DOCUMENTS section, 
            simply state that you don't know the answer.
                        
            QUESTION:
            {input}
                        
            DOCUMENTS:
            {documents}
                        
            """;

    public String simplify(String question) {

        PromptTemplate promptTemplate = new PromptTemplate(prompt);
        Map<String, Object> promptsParameters = new HashMap<>();
        promptsParameters.put("input", question);
        promptsParameters.put("documets", findSimilarData(question));

        return chatModel
                .call(promptTemplate.create(promptsParameters))
                .getResult()
                .getOutput()
                .getContent();
    }

    private String findSimilarData(String question) {
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(question).withTopK(5));

        return documents.stream()
                .map(Document::getContent)
                .collect(Collectors.joining());
    }
}










