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

import java.net.http.HttpHeaders;
import java.util.List;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsService {

    private final NewsMapper newsMapper;
    private final RestTemplate restTemplate;
    private final String OpenAiModel;
    private final String OpenAiUrl;


    public void crawlLatestNews () {

        String url = "https://www.yna.co.kr/news?site=navi_latest_depth01";

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

                System.out.println("제목: " + title);
                System.out.println("링크: " + link);

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
                        .categoryId(1)
                        .thumbnail(thumb)
                        .build();
                System.out.println("news : " + news);
                newsMapper.save(news);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 요약되지 않은 뉴스에 ai 추가
    public void generateSummaries() {
        // summary가 null인 뉴스 조회
        List<News> newsWithoutSummary = newsMapper.findNewsWithoutSummary();

        System.out.println("AI 요약 시작! 총 " + newsWithoutSummary.size() + "개");

        int successCount = 0;

        for (News news : newsWithoutSummary) {
            try {
                System.out.println("\n 요약 중: " + news.getTitle());

                String summary = summaryWithOpenAi(news.getContent());

                newsMapper.updateNewsSummary(news.getNewsId(), summary);
                successCount++;

                System.out.println("요약 완료 (" + successCount + "/" + newsWithoutSummary.size() + ")");

            } catch (Exception e) {
                System.err.println("AI 요약 실패: " + e.getMessage());
            }
        }

        System.out.println("AI 요약 완료! 총 " + successCount + "개");
    }

    // Open Ai 호출
    private String summaryWithOpenAi(String content) {
        String prompt = "다음 뉴스를 5줄 이하로 요약해줘:\n\n" + content;

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
