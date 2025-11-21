package com.ssr.newskuku.domain.openai;

import com.ssr.newskuku.domain.openai.dto.OpenAiRequest;
import com.ssr.newskuku.domain.openai.dto.OpenAiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class OpenAiService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String model;

    // @Qualifier로 Bean 이름 명시
    public OpenAiService(RestTemplate restTemplate,
                         @Qualifier("OpenAiUrl") String apiUrl,
                         @Qualifier("OpenAiModel") String model) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.model = model;

        // 디버깅
        System.out.println("=== OpenAI 서비스 초기화 ===");
        System.out.println("API URL: " + apiUrl);
        System.out.println("Model: " + model);
        System.out.println("Model이 null인가? " + (model == null));
    }

    public String getSummary(String newsContent) {
        if (newsContent == null || newsContent.trim().isEmpty()) {
            return "요약할 내용이 없습니다.";
        }

        String content = newsContent;
        if (content.length() > 3000) {
            content = content.substring(0, 3000) + "...";
        }

        String prompt = "다음 뉴스를 5줄 이하로 요약해줘:\n\n" + content;

        try {
            return sendRequestToOpenAi(prompt);
        } catch (Exception e) {
            System.err.println("OpenAI API 호출 실패: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("OpenAI 요청 중 오류 발생: " + e.getMessage(), e);
        }
    }

    public String chatWithOpenAi(String userMessage) {
        return sendRequestToOpenAi(userMessage);
    }

    private String sendRequestToOpenAi(String messageText) {
        System.out.println("요청 전 model 값: " + this.model);

        OpenAiRequest.Message message = new OpenAiRequest.Message("user", messageText);
        OpenAiRequest request = new OpenAiRequest(
                this.model,  // this.model 명시
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