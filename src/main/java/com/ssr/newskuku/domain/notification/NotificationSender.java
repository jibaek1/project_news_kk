package com.ssr.newskuku.domain.notification;

public interface NotificationSender {
    void send(String message);
    boolean supports(String type);
}
