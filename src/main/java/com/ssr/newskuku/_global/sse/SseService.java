package com.ssr.newskuku._global.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SseService {

    private final SseEmitterRepository repository;
    private static final Long DEFAULT_TIMEOUT = 1000L * 60 * 30;

    public SseEmitter connect(Long userId) {
        String emitterId = SseKey.generateId(userId);
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        repository.save(emitterId, emitter);

        try {
            emitter.send(SseEmitter.event()
                    .id("connect")
                    .name("connect")
                    .data("connected"));
        } catch (IOException e) {
            repository.remove(emitterId);
        }

        emitter.onCompletion(() -> repository.remove(emitterId));
        emitter.onTimeout(() -> repository.remove(emitterId));
        emitter.onError((e) -> repository.remove(emitterId));

        return emitter;
    }

    public void send(Long userId, Object data) {
        String emitterId = SseKey.generateId(userId);
        SseEmitter emitter = repository.get(emitterId);

        if (emitter == null) return;

        try {
            emitter.send(SseEmitter.event()
                    .name("notification")
                    .data(data));
        } catch (IOException e) {
            repository.remove(emitterId);
        }
    }
}
