package com.ssr.newskuku.domain.news;

import com.ssr.newskuku.domain.news.dto.NewsResponse;
import com.ssr.newskuku.domain.news.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsMapper newsMapper;
    private final JobLauncher jobLauncher;
    private final Job summarizeNewsJob;

    private final NewsCrawler crawler;
    private final NewsParser parser;

    private final List<String> categoryUrls = List.of(
            "https://www.yna.co.kr/politics/all",
            "https://www.yna.co.kr/economy/all",
            "https://www.yna.co.kr/market-plus/all",
            "https://www.yna.co.kr/industry/all",
            "https://www.yna.co.kr/society/all",
            "https://www.yna.co.kr/local/all",
            "https://www.yna.co.kr/international/all",
            "https://www.yna.co.kr/culture/all",
            "https://www.yna.co.kr/health/all",
            "https://www.yna.co.kr/entertainment/all",
            "https://www.yna.co.kr/sports/all"
    );

    // 뉴스 카테고리별 처리 크롤링 처리
    public void crawlAllCategoriesLatestNews() {

        int maxPage = 20;
        int totalSaved = 0;

        for (String categoryUrl : categoryUrls) {
            totalSaved += crawlCategory(categoryUrl, maxPage);
        }

        System.out.println("총 " + totalSaved + "개 기사 저장");
        // System.out.println("\n자동으로 AI 요약을 시작합니다...\n");

        // generateSummariesWithBatch();
    }

    // crawlCategory 메서드 수정: 저장한 개수 반환
    private int crawlCategory(String categoryUrl, int maxPage) {
        int savedCount = 0;

        boolean yesterdayStarted = false;
        int nonYesterdayCount = 0;

        for (int page = 1; page <= maxPage; page++) {

            try {
                Document doc = crawler.getDocument(categoryUrl + "/" + page);
                for (Element item : doc.select("div.news-con")) {

                    // 파싱
                    String title = parser.getTitle(item);
                    if (title.isBlank()) continue;

                    String link = parser.getLink(item);
                    if (link.isBlank()) continue;

                    // 중복 체크
                    if (newsMapper.existsByUrl(link) > 0) continue;

                    // 상세페이지
                    Document detail = crawler.getDocument(link);

                    // 상세 페이지 data-publishdate 가져오기
                    String realPublishedAt = parser.getRealPublishedAt(detail);
                    if (realPublishedAt.isBlank()) continue;

                    boolean isYesterday = parser.isYesterdayArticle(realPublishedAt);

                    // 어제 기사구간 시작했는데 어제가 아닌 기사가 나오면 -> 카운트 증가
                    if (!isYesterday && yesterdayStarted) {

                        nonYesterdayCount++;

                        // 20번 연속 어제가 아니면, 어제 기사 구간이 끝났다고 판단 -> 종료
                        if (nonYesterdayCount >= 20) {
                            return savedCount;
                        }

                        continue;
                    }

                    // 어제 기사만 저장
                    if (isYesterday) {

                        yesterdayStarted = true;
                        nonYesterdayCount = 0;
                        // News 생성
                        News news = News.builder()
                                .title(title)
                                .url(link)
                                .content(parser.getContent(detail))
                                .thumbnail(parser.getThumbnail(detail))
                                .category(parser.getCategory(detail))
                                .publishedAt(realPublishedAt)
                                .isWrite(true)
                                .summary(null)
                                .build();

                        newsMapper.save(news);
                        savedCount++;
                    }
                }
            } catch (Exception e) {
                System.err.println("카테고리 페이지 에러: " + e.getMessage());
            }
        }
        return savedCount;
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

    // 전체불러오기
    public List<NewsResponse.FindAll> findAll(int offset, int limit) {
        return newsMapper.findAll(offset, limit);
    }

    // 상세보기
    public NewsResponse.FindById getNewsId(Long id) {
        return newsMapper.findById(id);
    }
}
