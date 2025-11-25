package com.ssr.newskuku._global.batch.crawl;


import com.ssr.newskuku.domain.news.News;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class NewsCrawlBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final NewsCrawlReader reader;
    private final NewsCrawlProcessor processor;
    private final NewsCrawlWriter writer;

    @Bean
    public Step crawlStep(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager,
                          NewsCrawlReader reader,
                          NewsCrawlProcessor processor,
                          NewsCrawlWriter writer) {

        return new StepBuilder("crawlStep", jobRepository)
                .<String, News>chunk(50, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(999999)
                .build();
    }

    @Bean
    public Job crawlNewsJob(Step crawlStep) {
        return new JobBuilder("crawlNewsJob", jobRepository)
                .start(crawlStep)
                .build();
    }
}

