package com.ssr.newskuku._global.common;

import lombok.*;

@Data
public class PageLink {

    private int number;      // 1-based 표시용
    private int index;       // 0-based 실제 쿼리 파라미터용
    private boolean current; // 현재 페이지 여부

    @Builder
    public PageLink(int number, int index, boolean current) {
        this.number = number;
        this.index = index;
        this.current = current;
    }

}
