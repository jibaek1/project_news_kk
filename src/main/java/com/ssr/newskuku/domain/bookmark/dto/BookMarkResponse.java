package com.ssr.newskuku.domain.bookmark.dto;

import com.ssr.newskuku.domain.bookmark.BookMark;
import com.ssr.newskuku.domain.news.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookMarkResponse {

    @Getter
    public static class FindById {
        private final Long BookMarkId;
        private final Long userInfoId;
        private final Long newsId;
        private final String createdAt;

        public FindById(BookMark bookMark, Long userInfoId, Long newsId) {
            this.BookMarkId = bookMark.getBookmarkId();
            this.userInfoId = userInfoId;
            this.newsId = newsId;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.createdAt = bookMark.getCreatedAt() != null ? bookMark.getCreatedAt().format(formatter) : null;
        }

        private String nvl(String value) {
            return value != null ? value : "";
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindAll{
        private Long bookMarkId;
        private String createdAt;

        private Long newsId;
        private String title;
        private String summary;
        private String url;
        private int categoryId;
        private String thumbNail;

        public FindAll(BookMark bookMark, News news) {
            this.bookMarkId = bookMark.getBookmarkId();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.createdAt = bookMark.getCreatedAt() != null ? bookMark.getCreatedAt().format(formatter) : null;

            this.newsId = news.getNewsId();
            this.title = news.getTitle();
            this.url = news.getUrl();
            this.categoryId = news.getCategoryId();
            this.thumbNail = news.getThumbnail();

        }

        public static FindAll from(BookMark bookMark, News news){return new FindAll(bookMark, news);}

//        public static List<FindAll> from(List<BookMark> bookMarks, List<News> news) {
//            List<FindAll> dtoList = new ArrayList<>();
//            for (BookMark bookMark : bookMarks) {
//                dtoList.add(new FindAll(bookMark, new News()));
//            }
//            return dtoList;
//        }

    }

    private String nvl(String value) {
        return value != null ? value : "";
    }

}
