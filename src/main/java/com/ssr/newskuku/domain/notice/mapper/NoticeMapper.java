package com.ssr.newskuku.domain.notice.mapper;

import com.ssr.newskuku.domain.notice.dto.NoticeResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeMapper {

    List<NoticeResponse.List> findPage(Map<String, Object> params);
    int getTotalCount();

    NoticeResponse.Detail findById(Long id);
}
