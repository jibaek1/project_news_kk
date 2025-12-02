package com.ssr.newskuku.domain.notice.controller;



import com.ssr.newskuku.domain.notice.NoticeService;
import com.ssr.newskuku.domain.notice.dto.NoticeResponse;
import com.ssr.newskuku.domain.notice.dto.PageResult;
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
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String noticeMain(@RequestParam(defaultValue = "0") int page,
                             Model model) {

        int size = 10; // 페이지당 10개
        PageResult<NoticeResponse.List> noticePage = noticeService.getNoticePage(page, size);

        model.addAttribute("notices", noticePage.getItems());
        model.addAttribute("currentPage", noticePage.getPage());
        model.addAttribute("totalPages", noticePage.getTotalPages());
        model.addAttribute("hasNext", noticePage.hasNext());
        model.addAttribute("hasPrevious", noticePage.hasPrevious());

        return "notice/notice_list";
    }


    @GetMapping("/{id}")
    public String noticeDetail(@PathVariable Long id, Model model) {
        NoticeResponse.Detail detail = noticeService.findById(id);
        model.addAttribute("notice", detail);
        return "notice/notice_detail"; // /WEB-INF/views/notice/detail.jsp
    }

}
