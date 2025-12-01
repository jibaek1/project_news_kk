package com.ssr.newskuku.domain.news.controller;

import com.ssr.newskuku._global.common.PageLink;
import com.ssr.newskuku._global.common.PageUtils;
import com.ssr.newskuku.domain.news.News;
import com.ssr.newskuku.domain.news.NewsService;
import com.ssr.newskuku.domain.news.dto.NewsResponse;
import com.ssr.newskuku.domain.news.mapper.NewsMapper;
import com.ssr.newskuku.domain.userbookmark.UserBookMark;
import com.ssr.newskuku.domain.userbookmark.UserBookMarkService;
import com.ssr.newskuku.domain.userbookmark.mapper.UserBookMarkMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final JobLauncher jobLauncher;
    private final Job summarizeNewsJob;
    private final UserBookMarkService userBookMarkService;
    private final UserBookMarkMapper userBookMarkMapper;
    private final NewsMapper newsMapper;

    @GetMapping
    public String list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        int pageSize = 10;

        if (page < 0) page = 0;

        int offset = page * pageSize;

        List<NewsResponse.FindAll> newsList;
        int totalCount;

        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
            newsList = newsMapper.findByKeyword(keyword, offset, pageSize);
            totalCount = newsMapper.countByKeyword(keyword);
        } else {
            newsList = newsMapper.findAll(offset, pageSize);
            totalCount = newsMapper.countAll();
        }

        // 총 페이지수
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // PageUtils 사용
        List<PageLink> pageLinks = PageUtils.createPageLinks(page, pageSize, totalCount);

        model.addAttribute("newsList", newsList);
        model.addAttribute("pageLinks", pageLinks);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPages", totalPages);

        return "news/list";
    }

    // 모든 카테고리 최신기사 크롤링 테스트
    @GetMapping("/admin/crawl-all")
    public String crawlAllCategories() {
        newsService.crawlAllCategoriesLatestNews();
        return "모든 카테고리 크롤링 시작!";
    }

    // 상세보기
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        NewsResponse.FindById news = newsService.getNewsId(id);
        model.addAttribute("news", news);

        // 북마크 상태 확인 - 매퍼 직접 사용
        Long userInfoId = (Long) session.getAttribute("userInfoId");
        boolean isBookmarked = false;

        if (userInfoId != null) {
            UserBookMark bookmark = userBookMarkMapper.findByUserAndNews(userInfoId, id);
            isBookmarked = (bookmark != null);
        }

        model.addAttribute("isBookmarked", isBookmarked);

        return "news/detail";
    }

    // 북마크 토글
    @PostMapping("/bookmark/{newsId}")
    @ResponseBody
    public Map<String, Object> toggleBookmark(@PathVariable Long newsId, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        Long userInfoId = (Long) session.getAttribute("userInfoId");

        if (userInfoId == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        boolean bookmarked = userBookMarkService.toggle(userInfoId, newsId);

        response.put("success", true);
        response.put("bookmarked", bookmarked);
        return response;
    }
}
