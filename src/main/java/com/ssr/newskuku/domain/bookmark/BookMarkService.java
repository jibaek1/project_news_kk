package com.ssr.newskuku.domain.bookmark;

import com.ssr.newskuku.domain.bookmark.dto.BookMarkRequest;
import com.ssr.newskuku.domain.bookmark.dto.BookMarkResponse;
import com.ssr.newskuku.domain.bookmark.mapper.BookMarkMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookMarkService {

    private final BookMarkMapper bookMarkMapper;


    // 북마크 토글 기능
    @Transactional
    public boolean toggle(Long userInfoId, Long newsId) {
        BookMark exist = bookMarkMapper.findByUserAndNews(userInfoId, newsId);

        if (exist == null) {
            bookMarkMapper.insertBookMark(userInfoId, newsId);
            log.info("회원번호: {} 뉴스번호: {}", userInfoId, newsId);

            return true;

        } else {
            bookMarkMapper.deleteBookMark(userInfoId, newsId);
            log.info("회원번호: {} 뉴스번호: {}", userInfoId, newsId);
        }
        return false;
    }

    // 북마크 전체 조회 기능
    public List<BookMarkResponse.FindAll> findAllBookMark(Long userInfoId) {
        return bookMarkMapper.findAll(userInfoId);
    }



}
