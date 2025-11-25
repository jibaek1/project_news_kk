package com.ssr.newskuku.domain.news.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/batch")
public class NewsBatchController {

    private final JobLauncher jobLauncher;

    @Qualifier("crawlNewsJob")
    private final Job crawlNewsJob;

    @Qualifier("summarizeNewsJob")
    private final Job summarizeNewsJob;

    @GetMapping("/crawl")
    public String runCrawl() {
        try {
            jobLauncher.run(
                    crawlNewsJob,
                    new JobParametersBuilder()
                            .addLong("timestamp", System.currentTimeMillis())
                            .toJobParameters()
            );
            return "뉴스 크롤링 배치 실행됨";
        } catch (Exception e) {
            return "실패: " + e.getMessage();
        }
    }

    @GetMapping("/summary")
    public String runSummary() {
        try {
            jobLauncher.run(
                    summarizeNewsJob,
                    new JobParametersBuilder()
                            .addLong("timestamp", System.currentTimeMillis())
                            .toJobParameters()
            );
            return "뉴스 요약 배치 실행됨";
        } catch (Exception e) {
            return "실패: " + e.getMessage();
        }
    }
}
