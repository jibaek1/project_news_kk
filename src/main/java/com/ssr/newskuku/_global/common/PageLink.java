package com.ssr.newskuku._global.common;

import lombok.*;

@Data
public class PageLink {

    private String number;      // 1-based 표시용
    private int index;       // 0-based 실제 쿼리 파라미터용
    private boolean current; // 현재 페이지 여부

    @Builder
    public PageLink(int index, String number, boolean current) {
        this.index = index;
        this.number = number;
        this.current = current;
    }
}
