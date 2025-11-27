package com.ssr.newskuku.domain.news.dto;

import com.ssr.newskuku.domain.news.News;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NewsResponse {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindById {
        private Long newsId;
        private String title;
        private String content;
        private String summary;
        private String url;
        private String thumbnail;
        private String category;
        private String createdAt;
        private String modifiedAt;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindAll {
        private Long newsId;
        private String title;
        private String content;
        private String summary;
        private String url;
        private String thumbnail;
        private String category;
        private String createdAt;
        private String modifiedAt;
        private Integer viewCount; // 조회수
    }
}
