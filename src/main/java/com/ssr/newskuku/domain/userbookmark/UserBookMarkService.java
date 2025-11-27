package com.ssr.newskuku.domain.userbookmark;

import com.ssr.newskuku.domain.userbookmark.dto.UserBookMarkResponse;
import com.ssr.newskuku.domain.userbookmark.mapper.UserBookMarkMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserBookMarkService {

    private final UserBookMarkMapper userBookMarkMapper;


    // 북마크 토글 기능
    @Transactional
    public boolean toggle(Long sessionUserId, Long newsId) {
        UserBookMark exist = userBookMarkMapper.findByUserAndNews(sessionUserId, newsId);

        if (exist == null) {
            userBookMarkMapper.insertBookMark(sessionUserId, newsId);
            log.info("북마크 추가 - 회원번호: {} 뉴스번호: {}", sessionUserId, newsId);

            return true;

        } else {
            userBookMarkMapper.deleteBookMark(sessionUserId, newsId);
            log.info("북마크 삭제 - 회원번호: {} 뉴스번호: {}", sessionUserId, newsId);
        }
        return false;
    }

    // 북마크 전체 조회 기능
    public List<UserBookMarkResponse.FindAll> findAllBookMark(Long userId, int page, int size) {
        int offset = page * size;
        return userBookMarkMapper.findAll(userId, size, offset);
    }

    public int count(Long userId) {
        return userBookMarkMapper.countByUser(userId);
    }

}
