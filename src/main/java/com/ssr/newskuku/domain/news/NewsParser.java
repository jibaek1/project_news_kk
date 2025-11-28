package com.ssr.newskuku.domain.news;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class NewsParser {

    public String getLink(Element item) {
        return item.select("a.tit-news").attr("href");
    }

    public String getTitle(Element detail) {
        return detail.select("header.title-article01 > h1.tit01").text();
    }

    public LocalDateTime getPublishedAt(Document detail) {
        String dateStr = detail
                .select("div.update-time[data-published-time]")
                .attr("data-published-time");

        if (dateStr == null || dateStr.isBlank()) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateStr.trim(),formatter);
    }

    public boolean isYesterdayArticle(LocalDateTime publishedAt) {
        if (publishedAt == null) return false;

        LocalDate yesterday = LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(1);
        return publishedAt.toLocalDate().isEqual(yesterday);
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
