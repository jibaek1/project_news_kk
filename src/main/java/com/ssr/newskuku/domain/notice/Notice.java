package com.ssr.newskuku.domain.notice;


import com.ssr.newskuku._global.entity.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice extends BaseEntity {

    private Long noticeId;
    private String title;
    private String content;
    private Boolean isVisible;

}
