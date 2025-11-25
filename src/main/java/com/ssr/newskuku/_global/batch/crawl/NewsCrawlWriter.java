package com.ssr.newskuku._global.batch.crawl;

import com.ssr.newskuku.domain.news.News;
import com.ssr.newskuku.domain.news.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsCrawlWriter implements ItemWriter<News> {

    private final NewsMapper newsMapper;

    @Override
    public void write(Chunk<? extends News> chunk) {
        for (News news : chunk) {
            newsMapper.save(news);
            log.info("저장됨: {}", news.getTitle());
        }
    }
}
