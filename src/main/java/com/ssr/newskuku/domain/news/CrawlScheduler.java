package com.ssr.newskuku.domain.news;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlScheduler {

    private final NewsService newsService;

    @Scheduled(cron = "0 5 0 * * *") // 매일 00:05 실행
    public void crawlYesterdayNews() {
        log.info("=== 어제 뉴스 크롤링 시작 ===");
        newsService.crawlAllCategoriesLatestNews();
        log.info("=== 어제 뉴스 크롤링 끝 ===");
    }
}
