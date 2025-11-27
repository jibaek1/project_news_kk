package com.ssr.newskuku.domain.notification.listener;

import com.ssr.newskuku.domain.notification.Notification;
import com.ssr.newskuku.domain.notification.NotificationSender;
import com.ssr.newskuku.domain.notification.NotificationSenderFactory;
import com.ssr.newskuku.domain.notification.dto.NotificationEvent;
import com.ssr.newskuku.domain.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationMapper notificationMapper;   // ★ 추가
    private final NotificationSenderFactory senderFactory;

    @Async
    @EventListener
    public void handleNotificationEvent(NotificationEvent event) {

        // 1. DB 저장
        Notification noti = new Notification();
        noti.setUserId(event.getUserId());
        noti.setMessage(event.getMessage());
        noti.setType(event.getType());
        noti.setIsRead(false);

        notificationMapper.insert(noti);

        // 2. PUSH 전략일 때만 SSE 전송
        if ("PUSH".equalsIgnoreCase(event.getType())) {
            NotificationSender sender = senderFactory.findSender("PUSH");
            String payload = event.getUserId() + ":" + event.getMessage();
            sender.send(payload);
        }
    }
}
