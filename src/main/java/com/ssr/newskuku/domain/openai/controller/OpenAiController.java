package com.ssr.newskuku.domain.openai.controller;

import com.ssr.newskuku.domain.openai.OpenAiService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/openai")
public class OpenAiController {

    private final OpenAiService openAiService;

    public OpenAiController(OpenAiService openAIService) {
        this.openAiService = openAIService;
    }

    @GetMapping("/chat")
    public String chatWithAI(@RequestParam String message) {
        return openAiService.chatWithOpenAi(message);
    }
}
