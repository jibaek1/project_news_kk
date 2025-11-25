package com.ssr.newskuku._global.batch.crawl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class NewsCrawlReader implements ItemReader<String> {

    private final List<String> targets;
    private int index = 0;

    public NewsCrawlReader() {
        this.targets = createTargets();
    }

    private List<String> createTargets() {
        List<String> categoryUrls = List.of(
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

        int maxPage = 20;
        List<String> list = new ArrayList<>();

        for (String category : categoryUrls) {
            for (int page = 1; page <= maxPage; page++) {
                list.add(category + "/" + page);
            }
        }

        log.info("Reader 준비 완료. 총 {}개의 페이지 URL", list.size());
        return list;
    }

    @Override
    public String read() {
        if (index < targets.size()) {
            return targets.get(index++);
        }
        return null;
    }
}
