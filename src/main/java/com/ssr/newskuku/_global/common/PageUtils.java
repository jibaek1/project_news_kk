package com.ssr.newskuku._global.common;


import java.util.ArrayList;
import java.util.List;

public class PageUtils {

    public static List<PageLink> createPageLinks(int currentPage, int pageSize, int totalCount) {

        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        int blockSize = 5;  // 👉 원하는 숫자로 바꾸면 됨
        int blockStart = (currentPage / blockSize) * blockSize;    // ex) page=7 → 5
        int blockEnd = Math.min(blockStart + blockSize - 1, totalPages - 1); // ex) → 9 or max page

        List<PageLink> links = new ArrayList<>();

        // 이전 블록
        if (blockStart > 0) {
            links.add(new PageLink(blockStart - 1, "<<", false));  // label: “<<”
        }

        // 페이지 번호들
        for (int i = blockStart; i <= blockEnd; i++) {
            links.add(new PageLink(i, String.valueOf(i + 1), i == currentPage));
        }

        // 다음 블록
        if (blockEnd < totalPages - 1) {
            links.add(new PageLink(blockEnd + 1, ">>", false));  // label: “>>”
        }

        return links;
    }
}