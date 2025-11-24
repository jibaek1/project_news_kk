package com.ssr.newskuku.domain.notification.listener;

import com.ssr.newskuku.domain.notification.NotificationSenderFactory;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationSenderFactory notificationSenderFactory;



}
