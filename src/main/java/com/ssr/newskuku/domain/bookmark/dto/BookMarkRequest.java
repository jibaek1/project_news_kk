package com.ssr.newskuku.domain.bookmark.dto;

import com.ssr.newskuku.domain.news.News;
import lombok.Data;

public class BookMarkRequest {

    @Data
    public static class createBookMark {
        private Long BookMarkId;
        private Long userInfoId;
        private News news;
        private String createdAt;
        private boolean status;
    }

    @Data
    public static class deleteBookMark {
        private Long BookMarkId;
        private Long userInfoId;
        private News news;
        private String createdAt;
    }


}
