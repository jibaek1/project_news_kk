package com.ssr.newskuku.domain.news.dto;

import com.ssr.newskuku.domain.news.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        private Long category;
        private String createdAt;
        private String modifiedAt;
    }
}
