package com.ssr.newskuku.domain.notification.listener;

import com.ssr.newskuku.domain.notification.NotificationSender;
import com.ssr.newskuku.domain.notification.NotificationSenderFactory;
import com.ssr.newskuku.domain.notification.dto.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationSenderFactory notificationSenderFactory;


    @Async
    @EventListener
    public void handleNotificationEvent(NotificationEvent event) {
        NotificationSender sender = notificationSenderFactory.findSender(event.getType());
        String messageWithUser = event.getUserInfoId() + ":" + event.getMessage();
        sender.send(messageWithUser);
    }
}
