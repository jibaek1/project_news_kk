package com.ssr.newskuku.domain.openai;
import com.ssr.newskuku.domain.openai.dto.OpenAiRequest;
import com.ssr.newskuku.domain.openai.dto.OpenAiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Service
public class OpenAiService {

    private final RestTemplate restTemplate;
    private final String apiUrl = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    public OpenAiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String chatWithOpenAi(String userMessage) {
        OpenAiRequest.Message message = new OpenAiRequest.Message("user", userMessage);
        OpenAiRequest request = new OpenAiRequest(model, Collections.singletonList(message), 1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<OpenAiResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                OpenAiResponse.class
        );

        return response.getBody().getChoices().get(0).getMessage().getContent();
    }

}