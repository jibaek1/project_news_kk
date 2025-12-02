package com.ssr.newskuku.domain.notice.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResult<T> {

    private final List<T> items;
    private final int page;     // 현재 페이지(0-based)
    private final int size;     // 페이지당 개수
    private final int totalCount;

    public PageResult(List<T> items, int page, int size, int totalCount) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) totalCount / size);
    }

    public boolean hasNext() {
        return page < getTotalPages() - 1;
    }

    public boolean hasPrevious() {
        return page > 0;
    }
}
