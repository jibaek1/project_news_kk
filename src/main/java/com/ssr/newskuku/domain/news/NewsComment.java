package com.ssr.newskuku.domain.news;

import com.ssr.newskuku.domain.login.UserAccount;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsComment {
    private int newsCommentId;
    private int newsId;          // News와 연관관계 (FK)
    private int userId;          // User와 연관관계 (FK)
    private String userName;     // JOIN으로 가져온 사용자 이름
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // 연관관계 객체 (옵션 - 필요시 사용)
    private News news;           // 해당 댓글이 속한 뉴스 기사
    private UserAccount user;           // 댓글 작성자 정보
}
