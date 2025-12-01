package com.ssr.newskuku.domain.news;


import com.ssr.newskuku.domain.news.dto.NewsCommentDTO;
import com.ssr.newskuku.domain.news.mapper.NewsCommentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsCommentService {

    private final NewsCommentMapper mapper;

    public NewsCommentService(NewsCommentMapper mapper) {
        this.mapper = mapper;
    }

    // 뉴스별 댓글 목록 조회
    public List<NewsCommentDTO> getCommentsByNewsId(Long newsId) {
        return mapper.findByNewsId(newsId);
    }

    // 댓글 단일 조회
    public NewsCommentDTO getComment(Long id) {
        return mapper.findById(id);
    }

    // 댓글 작성
    public void addComment(NewsCommentDTO comment) {
        mapper.insert(comment);
    }

    // 댓글 수정
    public void updateComment(NewsCommentDTO comment) {
        mapper.update(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long id) {
        mapper.delete(id);
    }
}
