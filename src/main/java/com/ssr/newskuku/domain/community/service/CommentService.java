package com.ssr.newskuku.domain.community.service;

import com.ssr.newskuku.domain.community.Comment;
import com.ssr.newskuku.domain.community.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    // 1. 댓글 조회
    public List<Comment> getCommentsByPostId(Long communityId){
        return commentMapper.selectCommentsByPostId(communityId);
    }

    // 2. 댓글 작성
    public void addComment(Comment comment){
        commentMapper.insertComment(comment);
    }
}
