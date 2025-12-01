package com.ssr.newskuku.domain.news;


import com.ssr.newskuku._global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("News")
public class News extends BaseEntity {

    private Long newsId;
    private String title;
    private String content;
    private String url;
    private String category;
    private String thumbnail;
    private boolean isWrite;
    private LocalDateTime publishedAt;
    private Integer viewCount;

    // 뉴스 요약
    private String summary;

    private List<NewsComment> comments;
}
