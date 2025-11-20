package com.ssr.newskuku.domain.openai;
import com.ssr.newskuku._global.config.OpenAiConfig;
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
    private final String apiUrl;
    private final String model;

    public OpenAiService(RestTemplate restTemplate,
                         String OpenAiUrl,
                         String OpenAiModel) {
        this.restTemplate = restTemplate;
        this.apiUrl = OpenAiUrl;
        this.model = OpenAiModel;
    }

    public String chatWithOpenAi(String userMessage) {
        OpenAiRequest.Message message = new OpenAiRequest.Message("user", userMessage);
        OpenAiRequest request = new OpenAiRequest(model,
                Collections.singletonList(message),
                1
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OpenAiRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<OpenAiResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                OpenAiResponse.class
        );

        return response.getBody().getChoices().get(0).getMessage().getContent();
    }
}