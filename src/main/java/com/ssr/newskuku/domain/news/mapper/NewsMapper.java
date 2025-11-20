package com.ssr.newskuku.domain.news.mapper;

import com.ssr.newskuku.domain.news.News;
import com.ssr.newskuku.domain.news.dto.NewsResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsMapper {
    void save(News news);

    int existsByUrl(String url);

    // 상세보기
    NewsResponse.FindById findById(Long id);  // DTO로 직접 매핑

    // ai 테스트 용도
    News selectNewsById(@Param("newsId") Long newsId);
    void updateIsWrite(@Param("newsId") Long newsId, @Param("isWrite") int isWrite);
    void insertNewsSummary(@Param("newsId") Long newsId,
                           @Param("summaryText") String summary,
                           @Param("categoryId") Long categoryId);

}
