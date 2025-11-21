package com.ssr.newskuku.domain.news;

import com.ssr.newskuku.domain.news.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsMapper newsMapper;

    public void crawlLatestNews() {

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

}
