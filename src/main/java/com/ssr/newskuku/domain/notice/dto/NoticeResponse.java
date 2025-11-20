package com.ssr.newskuku.domain.notice.dto;


import com.ssr.newskuku.domain.notice.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NoticeResponse {

    @Getter
    public static class FindById{
        private final Long noticeId;
        private final String title;
        private final String content;
        private final Integer viewCount;
        private final boolean isVisible;
        private final String createdAt;
        private final String modifiedAt;

        public FindById(Notice notice) {
            this.noticeId = notice.getNoticeId();
            this.title = notice.getTitle();
            this.content = notice.getContent();
            this.viewCount = notice.getViewCount();
            this.isVisible = notice.getIsVisible();
            //this.isVisible = notice.getIsVisible() ? "공개" : "비공개";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            // view에 띄우기 위해 포맷(yyyy-MM-dd HH:mm)
            this.createdAt = notice.getCreatedAt() != null ? notice.getCreatedAt().format(formatter) : null;
            this.modifiedAt = notice.getModifiedAt() != null ? notice.getModifiedAt().format(formatter) : null;
        }

        private String nvl(String value) {
            return value != null ? value : "";
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindAll{
        private Long noticeId;
        private String title;
        private String content;
        private Integer viewCount;
        private boolean isVisible;
        private String createdAt;
        private String modifiedAt;

        public FindAll(Notice notice) {
            this.noticeId = notice.getNoticeId();
            this.title = notice.getTitle();
            this.content = notice.getContent();
            this.viewCount = notice.getViewCount();
            this.isVisible = notice.getIsVisible();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            // view에 띄우기 위해 포맷(yyyy-MM-dd HH:mm)
            this.createdAt = notice.getCreatedAt() != null ? notice.getCreatedAt().format(formatter) : null;
            this.modifiedAt = notice.getModifiedAt() != null ? notice.getModifiedAt().format(formatter) : null;
        }

        public static FindAll from(Notice notice){return new FindAll(notice);}

        public static List<FindAll> from(List<Notice> notices){
            List<FindAll> dtoList = new ArrayList<>();
            for(Notice notice : notices){
                dtoList.add(new FindAll(notice));
            }
            return dtoList;
        }

        private String nvl(String value) {
            return value != null ? value : "";
        }
    }
}
