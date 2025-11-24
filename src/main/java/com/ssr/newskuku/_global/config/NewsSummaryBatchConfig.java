package com.ssr.newskuku._global.config;

import com.ssr.newskuku.domain.news.News;
import com.ssr.newskuku.domain.news.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class NewsSummaryBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final NewsMapper newsMapper;
    private final RestTemplate restTemplate;
    private final String OpenAiModel;
    private final String OpenAiUrl;

    private AtomicInteger totalCount = new AtomicInteger(0);
    private AtomicInteger processedCount = new AtomicInteger(0);

    /**
     * TaskExecutor 설정 (병렬 처리용)
     * 이 설정이 핵심!
     */
    @Bean(name = "batchTaskExecutor")
    public TaskExecutor batchTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);      // 동시에 10개 처리
        executor.setMaxPoolSize(20);       // 최대 20개
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("batch-thread-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();

        System.out.println("TaskExecutor 초기화 완료: 병렬 처리 10개");
        return executor;
    }

    @Bean
    public ItemReader<News> newsReader() {
        return new ItemReader<News>() {
            private List<News> newsList;
            private int currentIndex = 0;

            @Override
            public News read() {
                if (newsList == null) {
                    newsList = newsMapper.findNewsWithoutSummary();
                    totalCount.set(newsList.size());
                    processedCount.set(0);
                    System.out.println("요약할 뉴스 총 " + newsList.size() + "개 발견");
                }

                if (currentIndex < newsList.size()) {
                    return newsList.get(currentIndex++);
                }
                return null;
            }
        };
    }

    @Bean
    public ItemProcessor<News, News> newsProcessor() {
        return news -> {
            try {
                int current = processedCount.incrementAndGet();
                int total = totalCount.get();

                // 스레드 이름 출력 (병렬 확인용)
                String threadName = Thread.currentThread().getName();
                System.out.println(String.format("[%s] 요약 중 (%d/%d): %s",
                        threadName, current, total, news.getTitle()));

                long startTime = System.currentTimeMillis();
                String summary = summaryWithOpenAi(news.getContent());
                long endTime = System.currentTimeMillis();

                if (summary != null) {
                    news.setSummary(summary);
                    System.out.println(String.format("[%s] 요약 완료 (%d/%d) [%dms]: %s",
                            threadName, current, total, (endTime - startTime), news.getTitle()));
                    return news;
                }

                return null;

            } catch (Exception e) {
                int current = processedCount.get();
                int total = totalCount.get();
                System.err.println(String.format("요약 실패 (%d/%d): %s", current, total, e.getMessage()));
                return null;
            }
        };
    }

    @Bean
    public ItemWriter<News> newsWriter() {
        return items -> {
            int savedCount = 0;
            for (News news : items) {
                if (news != null && news.getSummary() != null) {
                    newsMapper.updateNewsSummary(news.getNewsId(), news.getSummary());
                    savedCount++;
                }
            }
            if (savedCount > 0) {
                System.out.println(savedCount + "개 DB 저장 완료");
            }
        };
    }

    /**
     * Step 설정 - 여기가 핵심!
     */
    @Bean
    public Step summarizeStep(TaskExecutor batchTaskExecutor) {
        return new StepBuilder("summarizeStep", jobRepository)
                .<News, News>chunk(1, transactionManager)
                .reader(newsReader())
                .processor(newsProcessor())
                .writer(newsWriter())
                .taskExecutor(batchTaskExecutor)
                .throttleLimit(10)
                .build();
    }

    @Bean
    public Job summarizeNewsJob(Step summarizeStep) {
        return new JobBuilder("summarizeNewsJob", jobRepository)
                .start(summarizeStep)
                .build();
    }

    private String summaryWithOpenAi(String content) {
        String prompt = "다음 뉴스를 3줄 이하로 요약해줘:\n\n" + content;

        Map<String, Object> request = Map.of(
                "model", OpenAiModel,
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        try {
            Map response = restTemplate.postForObject(OpenAiUrl, request, Map.class);
            List choices = (List) response.get("choices");
            Map choice = (Map) choices.get(0);
            Map message = (Map) choice.get("message");

            return message.get("content").toString();

        } catch (Exception e) {
            return null;
        }
    }
}