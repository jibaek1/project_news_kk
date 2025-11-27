package com.ssr.newskuku.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class NotificationEvent {
    private Long userId; // 알림을 받을 사용자 ID
    private String message; // 알림 메시지
    private String type; // 알림 채널 타입 (e.g., "PUSH", "SMS")
    private boolean isRead; // 읽음 여부


}
