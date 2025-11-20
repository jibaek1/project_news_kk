package com.ssr.newskuku.domain.news.mapper;

import com.ssr.newskuku.domain.news.dto.NewsResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsMapper {

    // 상세보기
    NewsResponse.FindById findById(Long id);
}
