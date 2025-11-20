package com.ssr.newskuku.domain.openai.mapper;

import com.ssr.newskuku.domain.openai.dto.OpenAiResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OpenAiResponseMapper {
    void insertResponse(OpenAiResponse response);
}