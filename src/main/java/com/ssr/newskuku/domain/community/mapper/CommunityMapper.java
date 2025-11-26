package com.ssr.newskuku.domain.community.mapper;

import com.ssr.newskuku.domain.community.NewsCommunity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 커뮤니티 데이터베이스 접근 인터페이스
 *
 * "무엇을 할 것인가" 를 정의하는 계약서
 * 실제 SQL은 CommunityMapper.xml에 작성 됨
 * MyBatis가 이 인터페이스의 메서드와 XML의 쿼리를 자동으로 연결해줌
 */
@Mapper
public interface CommunityMapper {

    // 1. 게시글 전체 조회( int offset: 몇번째 부터 가져올 것인가, int limit: 몇개까지 가져올 것인가) <- 페이징
    List<NewsCommunity> selectAllCommunities(
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    // 2. 게시글 상세 조회
    NewsCommunity selectById(@Param("communityId") long communityId);

    // 3. 게시글 생성
    void insert(NewsCommunity newsCommunity);

    // 4. 게시글 수정
    void update(NewsCommunity newsCommunity);

}