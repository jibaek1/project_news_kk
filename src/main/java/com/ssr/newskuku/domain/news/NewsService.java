package com.ssr.newskuku.domain.news;

import com.ssr.newskuku.domain.news.dto.NewsResponse;
import com.ssr.newskuku.domain.news.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsMapper newsMapper;
    private final RestTemplate restTemplate;
    private final String OpenAiModel;
    private final String OpenAiUrl;


    public void crawlLatestNews() {

        int maxPage = 5;

        for (int page = 1; page <= maxPage; page++) {


            String url = "https://www.yna.co.kr/news/" + page + "?site=navi_latest_depth01";

            try {
                Document doc = Jsoup.connect(url).get();

                Elements items = doc.select("div.news-con");

                System.out.println("크롤링된 기사 수: " + items.size());

                for (Element item : items) {

                    // 제목
                    Element titleTag = item.selectFirst("span.title01");
                    if (titleTag == null) continue;
                    String title = titleTag.text();

                    // 링크
                    Element linkTag = item.selectFirst("a.tit-news");
                    if (linkTag == null) continue;
                    String link = linkTag.attr("href");

                    // 시간
                    String publishedAt = item.select("span.txt-time").text();

                    // 카테고리
                    String category = item.select("a.tit01").text();

                    System.out.println("제목: " + title);
                    System.out.println("링크: " + link);
                    System.out.println("시간: " + publishedAt);
                    System.out.println("카테고리: " + category);

                    if (newsMapper.existsByUrl(link) > 0) {
                        System.out.println("이미 저장된 기사 -> 스킵");
                        continue;
                    }

                    Document detail = Jsoup.connect(link).get();

                    String content = detail.select(".story-news.article p").text();
                    String thumb = detail.select(".img-con01 img").attr("src");

                    News news = News.builder()
                            .title(title)
                            .content(content)
                            .url(link)
                            .category(category)
                            .thumbnail(thumb)
                            .isWrite(true)
                            .publishedAt(publishedAt)
                            .build();

                    System.out.println("news : " + news);
                    newsMapper.save(news);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("크롤링 완료!");
        System.out.println("\n자동으로 AI 요약을 시작합니다...\n");
        generateSummaries();
    }

    // 요약되지 않은 뉴스에 ai 추가
    public void generateSummaries() {
        List<News> newsWithoutSummary = newsMapper.findNewsWithoutSummary();

        System.out.println("AI 요약 시작! 총 " + newsWithoutSummary.size() + "개");

        // 스레드 풀 생성 (최대 10개 동시 실행)
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // 완료 개수 추적 (멀티스레드 안전)
        AtomicInteger successCount = new AtomicInteger(0);
        int totalCount = newsWithoutSummary.size();

        // 비동기 작업 리스트
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 각 뉴스마다 비동기 작업 생성
        for (News news : newsWithoutSummary) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    System.out.println("요약 중: " + news.getTitle());

                    String summary = summaryWithOpenAi(news.getContent());

                    if (summary != null) {
                        summarizeAndSaveOne(news.getNewsId(), summary);
                        int completed = successCount.incrementAndGet();
                        System.out.println("요약 완료 (" + completed + "/" + totalCount + ")");
                    }

                } catch (Exception e) {
                    System.err.println("AI 요약 실패: " + e.getMessage());
                }
            }, executor);

            futures.add(future);
        }

        // 모든 작업 완료 대기
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // 스레드 풀 종료
        executor.shutdown();

        System.out.println(" AI 요약 완료! 총 " + successCount.get() + "개");
    }

    // 개별 저장 (트랜잭션 분리)
    @Transactional
    public void summarizeAndSaveOne(Long newsId, String summary) {
        newsMapper.updateNewsSummary(newsId, summary);
    }


    // Open Ai 호출
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
            System.out.println("요약 실패: " + e.getMessage());
            return null;
        }
    }

    // 상세보기
    public NewsResponse.FindById getNewsId(Long id) {
        return newsMapper.findById(id);
    }
}
