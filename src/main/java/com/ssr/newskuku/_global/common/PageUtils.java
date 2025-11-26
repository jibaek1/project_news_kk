package com.ssr.newskuku._global.common;


import java.util.ArrayList;
import java.util.List;

public class PageUtils {

    public static List<PageLink> createPageLinks(int currentPage, int size, int totalCount) {
        List<PageLink> links = new ArrayList<>();

        int totalPage = (int) Math.ceil((double) totalCount / size);

        for (int i = 0; i < totalPage; i++) {
            links.add(PageLink.builder()
                    .number(i + 1)
                    .index(i)
                    .current(i == currentPage)
                    .build());
        }

        return links;
    }
}
