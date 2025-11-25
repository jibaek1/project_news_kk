package com.ssr.newskuku.domain.notification.mapper;

import com.ssr.newskuku.domain.notification.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {

    void insert(Notification notification);

    List<Notification> findByUser(Long userId);

    int countUnread(Long userId);

    void markAsRead(@Param("id") Long id, @Param("userId") Long userId);
}