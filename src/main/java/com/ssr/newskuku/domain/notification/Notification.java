package com.ssr.newskuku.domain.notification;

import com.ssr.newskuku._global.entity.BaseEntity;

public class Notification extends BaseEntity {

    private Long notificationId;
    private Long userInfoId;
    private String message;
    private String type;
    private Boolean isRead;

}
