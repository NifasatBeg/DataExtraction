package com.nifasat.dataExtraction.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nifasat.dataExtraction.model.ExpenseDetails;
import com.nifasat.dataExtraction.model.LLMRequest;
import com.nifasat.dataExtraction.model.LLMResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LLMService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String apiUrl;
    private final String apiKey;

    @Autowired
    public LLMService(RestTemplate restTemplate, @Value("${llm.api.url}") String apiUrl,
                            @Value("${llm.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }

    public ExpenseDetails analyzeExpense(String message) {
        // Set up headers with API key
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        // Create the request body
        LLMRequest requestBody = new LLMRequest();
        requestBody.setModel("mistral-large-latest");

        List<LLMRequest.Message> messages = new ArrayList<>();

        // Add system message
        LLMRequest.Message systemMessage = new LLMRequest.Message();
        systemMessage.setRole("system");
        systemMessage.setContent("return response in json format, with parameters - (amount, merchant, currency). Strictly follow the response format.");
        messages.add(systemMessage);

        // Add user message
        LLMRequest.Message userMessage = new LLMRequest.Message();
        userMessage.setRole("user");
        userMessage.setContent(message);
        messages.add(userMessage);

        requestBody.setMessages(messages);

        // Set response format to JSON
        Map<String, String> responseFormat = new HashMap<>();
        responseFormat.put("type", "json_object");
        requestBody.setResponse_format(responseFormat);

        // Create the request entity
        HttpEntity<LLMRequest> requestEntity = new HttpEntity<>(requestBody, headers);

//         Make the API call
        LLMResponse response = restTemplate.postForObject(apiUrl, requestEntity, LLMResponse.class);

        try {
            // Parse the content from the response
            String content = response.getChoices().get(0).getMessage().getContent();
            JsonNode contentJson = objectMapper.readTree(content);

            return ExpenseDetails.builder()
                    .amount(contentJson.has("amount") ? contentJson.get("amount").asDouble() : null)
                    .merchant(contentJson.has("merchant") ? contentJson.get("merchant").asText() : null)
                    .currency(contentJson.has("currency") ? contentJson.get("currency").asText() : null)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Mistral AI response", e);
        }
    }
}
