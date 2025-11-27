package com.ssr.newskuku.domain.notification;

public class SmsNotificationSender implements NotificationSender{
    @Override
    public void send(String message) {
        System.out.println("SMS 발송: " + message);
    }

    @Override
    public boolean supports(String type) {
        return "SMS".equalsIgnoreCase(type);
    }
}
