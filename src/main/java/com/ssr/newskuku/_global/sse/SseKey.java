package com.ssr.newskuku._global.sse;

public class SseKey {
    public static String generateId(Long userId) {
        return "USER_" + userId;
    }
}
