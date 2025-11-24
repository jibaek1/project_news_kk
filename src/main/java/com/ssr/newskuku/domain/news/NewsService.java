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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsMapper newsMapper;
    private final JobLauncher jobLauncher;
    private final Job summarizeNewsJob;


    // 오늘 기사만 크롤링 하도록 처리
    private boolean isTodayArticle(String textTime) {
        // textTime: "11-21 15:47"
        if (textTime == null || textTime.isBlank()) return false;

        try {
            String datePart = textTime.split(" ")[0]; // "11-21"

            String today = LocalDate.now()
                    .format(DateTimeFormatter.ofPattern("MM-dd"));

            return datePart.equals(today);

        } catch (Exception e) {
            return false;
        }
    }


    // 뉴스 카테고리별 처리 크롤링 처리
    public void crawlAllCategoriesLatestNews() {

        List<String> categoryUrls = List.of(
                "https://www.yna.co.kr/politics/all",
                "https://www.yna.co.kr/economy/all",
                "https://www.yna.co.kr/market-plus/all",
                "https://www.yna.co.kr/industry/all",
                "https://www.yna.co.kr/society/all",
                "https://www.yna.co.kr/local/all",
                "https://www.yna.co.kr/world/all",
                "https://www.yna.co.kr/culture/all",
                "https://www.yna.co.kr/health/all",
                "https://www.yna.co.kr/entertainment/all",
                "https://www.yna.co.kr/sports/all"
        );

        int maxPage = 5;

        for (String baseUrl : categoryUrls) {
            crawlCategory(baseUrl, maxPage);
        }
    }

    private void crawlCategory(String categoryUrl, int maxPage) {

        for (int page = 1; page <= maxPage; page++) {

            String url = categoryUrl + "/" + page;


            try {

                Document doc = Jsoup.connect(url).get();

                Elements items = doc.select("div.news-con");

                for (Element item : items) {

                    // 제목
                    String title = item.select("span.title01").text();
                    // 링크
                    String link = item.select("a.tit-news").attr("href");
                    // 시간
                    String publishedAt = item.select("span.txt-time").text();

                    if (newsMapper.existsByUrl(link) > 0) continue;

                    // 상세 페이지
                    Document detail = Jsoup.connect(link).get();
                    // 내용
                    String content = detail.select(".story-news.article p").text();
                    // 썸네일
                    String thumb = detail.select(".img-con01 img").attr("src");
                    // 카테고리
                    String category = detail.select(".nav-path01 a[data-stat-code=bread_crumb]")
                            .first()
                            .text();

                    // 오늘 기사만
                    if (!isTodayArticle(publishedAt)) {
                        System.out.println("오늘 기사 아님 → 스킵: " + publishedAt);
                        continue;
                    }

                    // 시간이 없는 기사 → 광고/특수기사 → 스킵
                    if (publishedAt.isBlank()) {
                        System.out.println("시간 없는 기사 → 제외");
                        continue;
                    }

                    News news = News.builder()
                            .title(title)
                            .content(content)
                            .url(link)
                            .category(category)
                            .thumbnail(thumb)
                            .isWrite(true)
                            .publishedAt(publishedAt)
                            .build();

                    newsMapper.save(news);
                }
            } catch (Exception e) {
                System.out.println("크롤링 오류: " + e.getMessage());
            }
        }
    }


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
