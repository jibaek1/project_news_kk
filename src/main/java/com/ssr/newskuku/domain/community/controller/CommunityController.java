package com.ssr.newskuku.domain.community.controller;

import com.ssr.newskuku.domain.community.NewsCommunity;
import com.ssr.newskuku.domain.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 커뮤니티 요청을 처리하는 컨트롤러
 *
 * Controller의 역할:
 * 1. 사용자의 HTTP 요청을 받음
 * 2. Service를 호출해서 비즈니스 로직 실행
 * 3. Service에서 받은 결과를 Model에 담음
 * 4. JSP 파일명 반환
 */

@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    // 1. 전체 조회
    @GetMapping("")
    public String list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model
    ){
        List<NewsCommunity> communities = communityService.getAllCommunities(page, 10);
        model.addAttribute("communities", communities);
        model.addAttribute("page", page);
        return "community/list";
    }

    // 2. 상세보기
    @GetMapping("/id")
    public String detail(
            @RequestParam("communityId") long communityId, Model model){
        NewsCommunity community = communityService.getDetail(communityId);
        model.addAttribute("community", community);
        return "community/id";
    }

    // 3. 글 쓰기 폼 연결
    @GetMapping("/write")
    public String write(){
        return "community/write";
    }

    // 4. 글쓰기 저장
    @PostMapping("/write")
    public String writePost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("tag") String tag){
        NewsCommunity newsCommunity = new NewsCommunity();
        newsCommunity.setUserId(1L);
        newsCommunity.setTitle(title);
        newsCommunity.setContent(content);
        newsCommunity.setTag(tag);
        newsCommunity.setImgUrl(null);
        newsCommunity.setViewCount(0);

        communityService.create(newsCommunity);
        return "redirect:/community";
    }

    // 5. 게시글 수정 폼 보여주기
    @GetMapping("/edit")
    public String edit(@RequestParam("communityId") long communityId, Model model){
        NewsCommunity community = communityService.getDetail(communityId);
        model.addAttribute("community", community);
        return "community/edit";
    }

    // 6. 수정 데이터 저장
    @PostMapping("/edit")
    public String editPost(@RequestParam("communityId") long communityId,
                           @RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("tag") String tag){
        NewsCommunity newsCommunity = new NewsCommunity();
        newsCommunity.setCommunityId(communityId);
        newsCommunity.setTitle(title);
        newsCommunity.setContent(content);
        newsCommunity.setTag(tag);

        communityService.update(newsCommunity);
        return "redirect:/community/id?communityId=" + communityId;
    }

    // 7. 게시글 삭제
    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id){
        communityService.delete(id);
        return "redirect:/community";
    }
}
