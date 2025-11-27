package com.ssr.newskuku.domain.news.controller;

import com.ssr.newskuku.domain.news.NewsService;
import com.ssr.newskuku.domain.news.dto.NewsResponse;
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

    @GetMapping
    public String newsList(Model model) {
        List<NewsResponse.FindAll> newsList = newsService.findAll();
        model.addAttribute("news", newsList);
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

        // 북마크 상태 확인
        Long userInfoId = (Long) session.getAttribute("userInfoId");
        boolean isBookmarked = false;

        if (userInfoId != null) {
            isBookmarked = userBookMarkService.isBookmarked(userInfoId, id);
        }

        model.addAttribute("isBookmarked", isBookmarked);

        return "news/detail";
    }

    // 북마크 토글 (AJAX용)
    @PostMapping("/detail/{id}/bookmark")
    @ResponseBody
    public Map<String, Object> toggleBookmark(@PathVariable Long id, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        Long userInfoId = (Long) session.getAttribute("userInfoId");

        if (userInfoId == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        boolean bookmarked = userBookMarkService.toggle(userInfoId, id);

        response.put("success", true);
        response.put("bookmarked", bookmarked);
        return response;
    }
}
