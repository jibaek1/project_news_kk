package com.ssr.newskuku.domain.userbookmark.dto;

import com.ssr.newskuku.domain.userbookmark.UserBookMark;
import com.ssr.newskuku.domain.news.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

public class UserBookMarkResponse {

    @Getter
    public static class FindById {
        private final Long BookMarkId;
        private final Long userId;
        private final Long newsId;
        private final String createdAt;

        public FindById(UserBookMark userBookMark, Long userId, Long newsId) {
            this.BookMarkId = userBookMark.getBookmarkId();
            this.userId = userId;
            this.newsId = newsId;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.createdAt = userBookMark.getCreatedAt() != null ? userBookMark.getCreatedAt().format(formatter) : null;
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
        private String category;
        private String thumbNail;

        public FindAll(UserBookMark userBookMark, News news) {
            this.bookMarkId = userBookMark.getBookmarkId();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.createdAt = userBookMark.getCreatedAt() != null ? userBookMark.getCreatedAt().format(formatter) : null;

            this.newsId = news.getNewsId();
            this.title = news.getTitle();
            this.url = news.getUrl();
            this.category = news.getCategory();
            this.thumbNail = news.getThumbnail();

        }

        public static FindAll from(UserBookMark userBookMark, News news){return new FindAll(userBookMark, news);}

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
