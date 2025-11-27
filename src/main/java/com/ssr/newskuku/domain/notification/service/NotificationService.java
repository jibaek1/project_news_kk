package com.ssr.newskuku.domain.notification.service;

import com.ssr.newskuku.domain.notification.Notification;
import com.ssr.newskuku.domain.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMapper notificationMapper;

    public List<Notification> getNotifications(Long userId) {
        return notificationMapper.findByUser(userId);
    }

    public int getUnreadCount(Long userId) {
        return notificationMapper.countUnread(userId);
    }

    public void deleteNotification(Long id, Long userId) {
        notificationMapper.deleteById(id, userId);
    }
}
