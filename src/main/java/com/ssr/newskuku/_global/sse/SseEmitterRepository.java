package com.ssr.newskuku._global.sse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SseEmitterRepository {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void save(String emitterId, SseEmitter emitter) {
        emitters.put(emitterId, emitter);

    }

    public SseEmitter get(String emitterId) {
        return emitters.get(emitterId);
    }

    public void remove(String emitterId) {
        emitters.remove(emitterId);
    }


}
