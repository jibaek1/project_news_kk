package com.ssr.newskuku.domain.notice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class NoticeResponse {

    @Data
    @AllArgsConstructor
    public static class List {
        private Long noticeId;
        private String title;
        private String content;
        private boolean isVisible;
        private String createdAt;
        private String modifiedAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail {
        private Long noticeId;
        private String title;
        private String content;     // 전체 내용
        private boolean isVisible;
        private String createdAt;
        private String modifiedAt;
    }
}
