package com.ssr.newskuku.domain.news.dto;

import com.ssr.newskuku.domain.news.NewsComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsCommentDTO {
    private Long id;
    private Long newsId;
    private Long userId;
    private String writer;
    private String content;
    private String userName; // 조인해서 보여줄 이름
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

