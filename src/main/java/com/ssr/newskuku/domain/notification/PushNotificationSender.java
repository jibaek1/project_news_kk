package com.ssr.newskuku.domain.notification;

import com.ssr.newskuku._global.sse.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PushNotificationSender implements NotificationSender {

    private final SseService sseService;

    @Override
    public void send(String messageWithUser) {
        // 메시지에서 userId와 실제 메시지를 분리 (예: "1:새로운 댓글입니다")
        String[] parts = messageWithUser.split(":", 2);
        if (parts.length < 2) return; // 형식이 맞지 않으면 무시

        try {
            Long userId = Long.parseLong(parts[0]);
            String message = parts[1];
            sseService.send(userId, message);
        } catch (NumberFormatException e) {
            // userId 파싱 실패 시 처리
        }
    }

    @Override
    public boolean supports(String type) {
        return "PUSH".equalsIgnoreCase(type);
    }
}
