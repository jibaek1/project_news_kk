package com.ssr.newskuku.domain.community.service;

import com.ssr.newskuku.domain.community.NewsCommunity;
import com.ssr.newskuku.domain.community.mapper.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service 역할
 * Controller로부터 요청을 받음
 * 필요한 검증, 데이터 처리, 계산등을 수행
 * Mapper 호출해서 DB에 접근
 * 결과를 Controller로 반환
 * Controller와 Mapper 사이의 중간 계층
 */

@Service
public class CommunityService {

    @Autowired //spring의존성 주입 스프링이 CommunityMapper의 구현체를 자동으로 만들어서 여기에 주입함
    private CommunityMapper communityMapper;

    // 1. 전체 조회
    public List<NewsCommunity> getAllCommunities(int page, int size){
        int offset = (page - 1) * size;
        List<NewsCommunity> communities = communityMapper.selectAllCommunities(offset, size);

        return communities;
    }

    //  2. 상세보기
    public NewsCommunity getDetail(long communityId){
        return communityMapper.selectById(communityId);
    }

    // 3. 게시물 생성
    public void create(NewsCommunity newsCommunity){
        communityMapper.insert(newsCommunity);
    }

    // 4. 게시글 수정
    public void  update(NewsCommunity newsCommunity){
        communityMapper.update(newsCommunity);
    }

    // 5. 게시글 삭제
    public void  delete(Long communityId){
        communityMapper.delete(communityId);
        }

}
