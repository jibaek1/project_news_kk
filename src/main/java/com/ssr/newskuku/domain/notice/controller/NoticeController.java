package com.ssr.newskuku.domain.notice.controller;



import com.ssr.newskuku.domain.notice.NoticeService;
import com.ssr.newskuku.domain.notice.dto.NoticeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String noticeMain(@RequestParam(defaultValue = "0") int page,
                             Model model) {
        List<NoticeResponse> noticeList = noticeService.getNoticeList();

        /*
        int totalPages = noticePage.getTotalPages();
        int currentPage = noticePage.getNumber(); // 0-based
        List<PageLink> pageInfos = IntStream.range(0, totalPages)
                .mapToObj(i -> new PageLink(i + 1, i, i == currentPage))
                .collect(Collectors.toList());
        */

        // Mustache 에서 사용 할 값 넘겨주는 Model
        model.addAttribute("notices", noticeList); // 등록되어있는 질문 리스트
        /*
        model.addAttribute("currentPage", noticePage.getNumber()+ 1); // 현재 페이지
        model.addAttribute("totalPages", noticePage.getTotalPages()); // 전체 페이지 수
        model.addAttribute("hasNext", noticePage.hasNext()); // 다음 페이지 존재 여부
        model.addAttribute("hasPrevious", noticePage.hasPrevious()); // 이전페이지 존재 여부
        model.addAttribute("nextPage", noticePage.hasNext() ? noticePage.getNumber() + 1 : noticePage.getNumber()); // 다음페이지 번호
        model.addAttribute("prevPage", noticePage.hasPrevious() ? noticePage.getNumber() - 1 : noticePage.getNumber()); // 이전페이지 번호
        model.addAttribute("pageInfos", pageInfos); // 페이지 전체정보
        model.addAttribute("totalElements", noticePage.getTotalElements()); // 전체 질문 수
        model.addAttribute("pageSize", noticePage.getSize()); // 한페이지당 표시 개수 : 10
        */

        return "notice/notice_list";
    }
}
