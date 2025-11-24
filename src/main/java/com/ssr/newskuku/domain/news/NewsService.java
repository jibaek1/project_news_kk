package com.ssr.newskuku.domain.news;

import com.ssr.newskuku.domain.news.dto.NewsResponse;
import com.ssr.newskuku.domain.news.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;



import java.io.IOException;


@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsMapper newsMapper;
    private final JobLauncher jobLauncher;
    private final Job summarizeNewsJob;


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
                            .summary(null)  // AI 요약은 Batch에서!
                            .build();

                    System.out.println("news : " + news);
                    newsMapper.save(news);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("크롤링 완료!");
        System.out.println("\n 자동 요약 시작\n");

        generateSummariesWithBatch();
    }

    private void generateSummariesWithBatch() {
        try {
            System.out.println("AI 요약 시작!");

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(summarizeNewsJob, jobParameters);

            System.out.println("AI 요약 완료!");

        } catch (Exception e) {
            System.err.println("Batch 실행 실패: " + e.getMessage());
            throw new RuntimeException("AI 요약 중 오류 발생", e);
        }
    }

    // 상세보기
    public NewsResponse.FindById getNewsId(Long id) {
        return newsMapper.findById(id);
    }
}
