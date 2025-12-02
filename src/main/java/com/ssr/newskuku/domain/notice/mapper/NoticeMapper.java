package com.ssr.newskuku.domain.notice.mapper;

import com.ssr.newskuku.domain.notice.dto.NoticeResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {

    List<NoticeResponse.List> findAll();
}
