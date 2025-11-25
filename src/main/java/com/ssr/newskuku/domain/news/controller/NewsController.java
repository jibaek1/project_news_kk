package com.ssr.newskuku.domain.news.controller;

import com.ssr.newskuku.domain.news.News;
import com.ssr.newskuku.domain.news.NewsService;
import com.ssr.newskuku.domain.news.dto.NewsResponse;
import com.ssr.newskuku.domain.news.mapper.NewsMapper;
import com.ssr.newskuku.domain.openai.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final JobLauncher jobLauncher;
    private final Job summarizeNewsJob;

    @GetMapping
    public String newsList() {
        return "news/list";
    }


    // 모든 카테고리 최신기사 크롤링 테스트
    @GetMapping("/admin/crawl-all")
    public String crawlAllCategories() {
        newsService.crawlAllCategoriesLatestNews();
        return "모든 카테고리 크롤링 시작!";
    }

    /**
     * 임시 크롤링 API
     * @return
     */
    @GetMapping("/admin/crawl")
    @ResponseBody
    public String crawl(String url) {
        newsService.crawlLatestNews();
        return "크롤링 및 요약 완료";
    }

    // 상세보기
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        NewsResponse.FindById news = newsService.getNewsId(id);
        model.addAttribute("news", news);
        return "news/detail";  // JSP 페이지
    }
}
