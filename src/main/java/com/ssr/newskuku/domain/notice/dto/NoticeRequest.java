package com.ssr.newskuku.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class NoticeRequest {


    @Getter
    @Setter
    @ToString
    public static class CreateNotice {
        private Long noticeId;
        private String title;
        private String noticeContent;
        private Integer viewCount;
        @JsonProperty("isVisible")
        private boolean isVisible;
        private String createdAt;
        private String modifiedAt;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateNotice {
        private Long noticeId;
        private String title;
        private String noticeContent;
        private Integer viewCount;
        @JsonProperty("isVisible")
        private boolean isVisible;
        private String createdAt;
        private String modifiedAt;
    }
}
