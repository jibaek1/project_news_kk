package com.ssr.newskuku.domain.news.controller;

import com.ssr.newskuku.domain.news.News;
import com.ssr.newskuku.domain.news.NewsService;
import com.ssr.newskuku.domain.news.dto.NewsResponse;
import com.ssr.newskuku.domain.news.mapper.NewsMapper;
import com.ssr.newskuku.domain.openai.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final NewsMapper newsMapper;
    private final OpenAiService openAiService;

    /**
     * 임시 크롤링 API
     * @return
     */
    @GetMapping("/admin/crawl")
    @ResponseBody
    public String crawl() {
        newsService.crawlLatestNews();
        return "크롤링 완료";
    }

    // 요약 생성
    @GetMapping("/admin/summarize")
    @ResponseBody
    public String summarize() {
        try {
            newsService.generateSummaries();
            return "✅ AI 요약 완료!";
        } catch (Exception e) {
            return "❌ AI 요약 실패: " + e.getMessage();
        }
    }

    // 상세보기
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        NewsResponse.FindById news = newsService.getNewsId(id);
        model.addAttribute("news", news);
        return "news/detail";  // JSP 페이지
    }
}
