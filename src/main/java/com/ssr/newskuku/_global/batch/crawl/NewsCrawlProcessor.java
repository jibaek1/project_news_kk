package com.ssr.newskuku._global.batch.crawl;

import com.ssr.newskuku.domain.news.News;
import com.ssr.newskuku.domain.news.NewsCrawler;
import com.ssr.newskuku.domain.news.NewsParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsCrawlProcessor implements ItemProcessor<String, News> {

    private final NewsCrawler crawler;
    private final NewsParser parser;

    private List<News> buffer = new ArrayList<>();
    private int bufferIndex = 0;

    @Override
    public News process(String url) {

        try {
            if (bufferIndex >= buffer.size()) {

                Document doc = crawler.getDocument(url);

                List<News> temp = new ArrayList<>();
                for (Element item : doc.select("div.news-con")) {

                    try {
                        String title = parser.getTitle(item);
                        if (title.isBlank()) continue;

                        String link = parser.getLink(item);
                        if (link.isBlank()) continue;

                        Document detail = crawler.getDocument(link);

                        String publishedAt = parser.getRealPublishedAt(detail);
                        if (publishedAt.isBlank()) continue;

                        if (!parser.isYesterdayArticle(publishedAt)) continue;

                        News news = News.builder()
                                .title(title)
                                .url(link)
                                .content(parser.getContent(detail))
                                .thumbnail(parser.getThumbnail(detail))
                                .category(parser.getCategory(detail))
                                .publishedAt(publishedAt)
                                .isWrite(true)
                                .summary(null)
                                .build();

                        temp.add(news);

                    } catch (Exception e) {
                        log.error("상세기사 파싱 실패 → skip: {}", e.getMessage());
                        continue; // 상세페이지 실패해도 계속 진행
                    }
                }

                buffer = temp;
                bufferIndex = 0;

                if (buffer.isEmpty()) return null;
            }

            return buffer.get(bufferIndex++);

        } catch (Exception ex) {
            log.error("목록 페이지 크롤링 실패 → skip: {}", ex.getMessage());
            return null; // 이러면 Step은 절대 FAIL 안 됨
        }
    }
}

