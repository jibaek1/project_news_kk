package com.ssr.newskuku.domain.notification;

public class SmsNotificationSender implements NotificationSender{
    @Override
    public void send(String message) {

    }

    @Override
    public boolean supports(String type) {
        return false;
    }
}
