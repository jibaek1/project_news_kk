package com.ssr.newskuku.domain.news;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class NewsParser {

    public String getTitle(Element item) {
        return item.select("span.title01").text();
    }

    public String getLink(Element item) {
        return item.select("a.tit-news").attr("href");
    }

    public String getRealPublishedAt(Document detail) {
        String date = detail.select(".update-time").attr("data-published-time");
        return date != null ? date.trim() : "";
    }

    public boolean isYesterdayArticle(String dateTime) {
        if (dateTime == null || dateTime.isBlank()) return false;

        try {
            String datePart = dateTime.split(" ")[0];

            String yesterday = LocalDate.now()
                    .minusDays(1)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            return datePart.equals(yesterday);
        } catch (Exception e) {
            return false;
        }
    }

    public String getContent(Document doc) {
        return doc.select(".story-news.article p").text();
    }

    public String getThumbnail(Document doc) {
        return doc.select(".img-con01 img").attr("src");
    }

    public String getCategory(Document doc) {
        Element e = doc.select(".nav-path01 a[data-stat-code=bread_crumb]").first();
        return e != null ? e.text() : "기타";
    }
}
