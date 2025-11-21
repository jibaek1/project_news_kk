package com.ssr.newskuku.domain.bookmark.mapper;

import com.ssr.newskuku.domain.bookmark.BookMark;
import com.ssr.newskuku.domain.bookmark.dto.BookMarkResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMarkMapper {

        // 존재 확인
        BookMark findByUserAndNews(@Param("userInfoId") Long userInfoId,
                                   @Param("newsId") Long newsId);

        // 생성
        void insertBookMark(@Param("userInfoId") Long userInfoId,
                            @Param("newsId") Long newsId);

        // 삭제
        void deleteBookMark(@Param("userInfoId") Long userInfoI,
                            @Param("newsId") Long newsId);

        // 전체 조회
        List<BookMarkResponse.FindAll> findAll(@Param("userInfoId") Long userInfoId);

}
