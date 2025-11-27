package com.ssr.newskuku.domain.userbookmark.dto;

import lombok.Data;

public class UserBookMarkRequest {

    @Data
    public static class createBookMark {
        private Long BookMarkId;
        private Long userId;
        private Long newsId;
        private String createdAt;
    }

    @Data
    public static class deleteBookMark {
        private Long BookMarkId;
        private Long userId;
        private Long newsId;
        private String createdAt;
    }

}
