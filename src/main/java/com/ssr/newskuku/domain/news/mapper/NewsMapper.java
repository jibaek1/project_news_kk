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

    // ========================================
    // 요약 관련 메서드 - XML에 정의되어 있음
    // ========================================

    // summary가 null인 뉴스 조회 - XML 사용
    List<News> findNewsWithoutSummary();

    // 요약 업데이트 - XML 사용
    void updateNewsSummary(@Param("newsId") Long newsId, @Param("summary") String summary);

    // ========================================
    // AI 테스트 용도 (TODO: 테스트 완료 후 삭제 예정)
    // ========================================

    News selectNewsById(@Param("newsId") Long newsId);

    void updateIsWrite(@Param("newsId") Long newsId, @Param("isWrite") int isWrite);

    void insertNewsSummary(@Param("newsId") Long newsId,
                           @Param("summaryText") String summary,
                           @Param("categoryId") Long categoryId);
}