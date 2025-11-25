package com.ssr.newskuku.domain.notice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponse {

    private Long noticeId;
    private String title;
    private String content;
    private Integer viewCount;
    private boolean isVisible;
    private String createdAt;
    private String modifiedAt;


}
