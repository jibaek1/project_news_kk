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
    public List<UserBookMarkResponse.FindAll> findAllBookMark(
            Long sessionUserId,
            Long userId,
            int page,
            int size
    ) {
        if (sessionUserId == null) {
            throw new IllegalArgumentException("로그인이 필요한 서비스입니다.");
        }

        if (!sessionUserId.equals(userId)) {
            throw new IllegalArgumentException("권한이 없습니다. 본인만 조회할 수 있습니다.");
        }

        int offset = page * size;
        return userBookMarkMapper.findAll(userId, size, offset);
    }

    public int count(Long userId) {
        return userBookMarkMapper.countByUser(userId);
    }

}
