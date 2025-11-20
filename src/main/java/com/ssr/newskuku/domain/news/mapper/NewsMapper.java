package com.ssr.newskuku.domain.news.mapper;

import com.ssr.newskuku.domain.news.News;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsMapper {
    void save(News news);

    int existsByUrl(String url);
    // 상세보기
    NewsResponse.FindById findById(Long id);
}
