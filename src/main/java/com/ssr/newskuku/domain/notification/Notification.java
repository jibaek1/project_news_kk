package com.ssr.newskuku.domain.notification;

import com.ssr.newskuku._global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {

    private Long notificationId;
    private Long userId;
    private String message;
    private String type;
    private Boolean isRead;

}
