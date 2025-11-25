package com.ssr.newskuku.domain.userbookmark.mapper;

import com.ssr.newskuku.domain.userbookmark.UserBookMark;
import com.ssr.newskuku.domain.userbookmark.dto.UserBookMarkResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserBookMarkMapper {

        // 존재 확인
        UserBookMark findByUserAndNews(@Param("userInfoId") Long userInfoId,
                                       @Param("newsId") Long newsId);

        // 생성
        void insertBookMark(@Param("userInfoId") Long userInfoId,
                            @Param("newsId") Long newsId);

        // 삭제
        void deleteBookMark(@Param("userInfoId") Long userInfoId,
                            @Param("newsId") Long newsId);

        // 전체 조회
        List<UserBookMarkResponse.FindAll> findAll(@Param("userInfoId") Long userInfoId,
                                                        @Param("size") int size,
                                                        @Param("offset") int offset);

    int countByUser(@Param("userInfoId") Long userInfoId);

}
