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

    public String getPublishedAt(Element item) {
        return item.select("span.txt-time").text();
    }

    public boolean isTodayArticle(String textTime) {
        if (textTime == null || textTime.isBlank()) return false;

        try {
            String datePart = textTime.split(" ")[0];
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));
            return datePart.equals(today);
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
