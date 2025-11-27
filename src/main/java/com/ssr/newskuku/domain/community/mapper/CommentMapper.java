package com.ssr.newskuku.domain.community.mapper;

import com.ssr.newskuku.domain.community.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    // 1. 댓글 조회
    List<Comment> selectCommentsByPostId(@Param("communityId") Long communityId);

    // 2. 댓글 작성
    void insertComment(Comment comment);

    // 3. 댓글 수정
    void updateComment(Comment comment);

    // 4. 댓글 삭제
    void deleteComment(@Param("communityId") Long commentsId);
}