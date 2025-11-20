package com.ssr.newskuku.domain.notice;



import com.ssr.newskuku.domain.notice.dto.NoticeResponse;

import com.ssr.newskuku.domain.notice.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeMapper noticeMapper;


    public List<NoticeResponse.FindAll> getNoticeList(){

        return noticeMapper.findAll();
    }

}
