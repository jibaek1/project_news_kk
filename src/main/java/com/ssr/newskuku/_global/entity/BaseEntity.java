package com.ssr.newskuku._global.entity;



import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Getter
public class BaseEntity {

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
