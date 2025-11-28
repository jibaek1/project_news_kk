package com.ssr.newskuku.domain.community.controller;

import com.ssr.newskuku._global.common.PageLink;
import com.ssr.newskuku._global.common.PageUtils;
import com.ssr.newskuku.domain.community.NewsCommunity;
import com.ssr.newskuku.domain.community.mapper.CommunityMapper;
import com.ssr.newskuku.domain.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private CommunityMapper communityMapper;

    // 1. 전체 조회
    @GetMapping
    public String list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            Model model
    ){
    int pageSize = 10;
    if (page < 0) {
        page = 0;
    }

    int offset = page * pageSize;

    List<NewsCommunity> communities;
    int totalCount;

        if (keyword != null && !keyword.trim().isEmpty()) {
            communities = communityMapper.findByKeyword(keyword.trim(), offset, pageSize);
            totalCount = communityMapper.countByKeyword(keyword.trim());
        } else {
            communities = communityMapper.selectAllCommunities(offset, pageSize);
            totalCount = communityMapper.countAll();
        }

    List<PageLink> pageLinks = PageUtils.createPageLinks(page, pageSize, totalCount);

    model.addAttribute("communities", communities);
    model.addAttribute("pageLinks", pageLinks);
    model.addAttribute("page", page);
    model.addAttribute("keyword", keyword);

    return "community/list";
    }

    // 2. 상세보기
    @GetMapping("/detail")
    public String detail(
            @RequestParam("communityId") long communityId, Model model){
        NewsCommunity community = communityService.getDetail(communityId);
        model.addAttribute("community", community);
        return "community/detail";
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

    // 5. 게시글 수정 폼 보여주기 (GET)
    @GetMapping("/edit")
    public String edit(@RequestParam("communityId") long communityId, Model model){
        NewsCommunity community = communityService.getDetail(communityId);
        model.addAttribute("community", community);
        return "community/edit";  // ✅ edit.jsp를 보여주기만 함
    }

    // 6. 게시글 수정 저장 (POST)
    @PostMapping("/edit")
    public String editPost(
            @RequestParam("communityId") long communityId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("tag") String tag){
        NewsCommunity newsCommunity = new NewsCommunity();
        newsCommunity.setCommunityId(communityId);
        newsCommunity.setTitle(title);
        newsCommunity.setContent(content);
        newsCommunity.setTag(tag);

        communityService.update(newsCommunity);
        return "redirect:/community/detail?communityId=" + communityId;
    }

    // 7. 게시글 삭제
    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id){
        communityService.delete(id);
        return "redirect:/community";
    }
}