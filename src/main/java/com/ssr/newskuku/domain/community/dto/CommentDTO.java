package com.ssr.newskuku.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {

    private Long commentsId;
    private Long communityId;
    private Long userId;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
