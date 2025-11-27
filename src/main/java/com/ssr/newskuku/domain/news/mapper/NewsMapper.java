package com.ssr.newskuku.domain.news.mapper;

import com.ssr.newskuku.domain.news.News;
import com.ssr.newskuku.domain.news.dto.NewsResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsMapper {

    // 뉴스 저장
    void save(News news);

    // URL 중복 체크
    int existsByUrl(String url);

    // 상세보기
    NewsResponse.FindById findById(Long id);

    // summary가 null인 뉴스 조회
    List<News> findNewsWithoutSummary();

    // 요약 업데이트
    void updateNewsSummary(@Param("newsId") Long newsId, @Param("summary") String summary);

    // 뉴스 전체보기
    List<NewsResponse.FindAll> findAll();
}