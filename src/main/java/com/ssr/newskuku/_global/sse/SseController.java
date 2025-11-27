package com.ssr.newskuku._global.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sse")
public class SseController {

    private final SseService sseService;

    @GetMapping("/connect")
    public SseEmitter connect(@RequestParam Long userInfoId) {
        return sseService.connect(userInfoId);
    }
}
