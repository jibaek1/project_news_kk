package com.ssr.newskuku.domain.news.controller;

import com.ssr.newskuku.domain.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NewController {

    private final NewsService newsService;

    /**
     * 임시 크롤링 API
     * @return
     */

    @GetMapping("/admin/clawl")
    public String crawl() {
        newsService.crawlLatestNews();
        return "크롤링 완료";
    }
}
