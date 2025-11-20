package com.ssr.newskuku.domain.news.dto;

import com.ssr.newskuku.domain.news.News;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

public class NewsResponse {

    @Getter
    public static class FindById {
        private final Long newsId;
        private final String title;
        private final String content;
        private final String url;
        private final String createdAt;
        private final String modifiedAt;


        public FindById(News news) {
            this.newsId = news.getNewsId();
            this.title = news.getTitle();
            this.content = news.getContent();
            this.url = news.getUrl();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            this.createdAt = news.getCreatedAt() != null ? news.getCreatedAt().format(formatter) : null;
            this.modifiedAt = news.getModifiedAt() != null ? news.getModifiedAt().format(formatter) : null;
        }
    }
}
