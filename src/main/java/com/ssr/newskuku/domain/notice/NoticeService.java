package com.ssr.newskuku.domain.notice;


import com.ssr.newskuku.domain.notice.dto.NoticeResponse;
import com.ssr.newskuku.domain.notice.dto.PageResult;
import com.ssr.newskuku.domain.notice.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeMapper noticeMapper;

    public PageResult<NoticeResponse.List> getNoticePage(int page, int size) {

        int offset = page * size;

        List<NoticeResponse.List> items =
                noticeMapper.findPage(Map.of("offset", offset, "size", size));

        int totalCount = noticeMapper.getTotalCount();

        return new PageResult<>(items, page, size, totalCount);
    }

    public NoticeResponse.Detail findById(Long id) {
        return noticeMapper.findById(id);
    }


}
