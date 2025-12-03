package com.ssr.newskuku.domain.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class NewsCrawler {

    public Document getDocument(String url) throws Exception {

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                        .referrer("https://www.google.com")
                        .timeout(15000)
                        .maxBodySize(0)
                        .ignoreHttpErrors(true)
                        .ignoreContentType(true)
                        .get();

                // 0.3~0.7초 랜덤 딜레이 (봇 차단 방지)
                Thread.sleep(300 + (int)(Math.random() * 400));

                return doc;

            } catch (Exception e) {
                System.out.println("[실패 " + attempt + "회] " + url + " | " + e.getMessage());

                if (attempt == 3) {
                    System.out.println("3회 실패 → 최종 포기: " + url);
                    throw e;
                }

                Thread.sleep(1000); // 1초 쉬고 재시도
            }
        }

        return null; // 이 줄은 사실 올 일이 없음
    }

}
