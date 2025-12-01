package com.ssr.newskuku.domain.news.mapper;

import com.ssr.newskuku.domain.news.NewsComment;
import com.ssr.newskuku.domain.news.dto.NewsCommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsCommentMapper {

    List<NewsCommentDTO> findByNewsId(Long newsId);

    NewsCommentDTO findById(Long id);

    int insert(NewsCommentDTO comment);

    int update(NewsCommentDTO comment);

    int delete(Long id);
}