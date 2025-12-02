package com.ssr.newskuku.domain.userbookmark.controller;

import com.ssr.newskuku._global.common.Define;
import com.ssr.newskuku._global.common.PageLink;
import com.ssr.newskuku._global.common.PageUtils;
import com.ssr.newskuku.domain.userbookmark.UserBookMarkService;
import com.ssr.newskuku.domain.userbookmark.dto.UserBookMarkResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class UserBookMarkController {

    private final UserBookMarkService userBookMarkService;

    @PostMapping("/toggle")
    @ResponseBody
    public boolean toggle(@RequestParam Long newsId,
                          @RequestParam Long userId, HttpSession session) {

        Long sessionUserId = (Long) session.getAttribute(Define.SESSION_USER);
        if (sessionUserId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        return userBookMarkService.toggle(userId, newsId);
    }

    @GetMapping("/findAllBookMark")
    public String getFindAllList(@RequestParam Long userId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {

        List<UserBookMarkResponse.FindAll> list =
                userBookMarkService.findAllBookMark(userId, page, size);

        int total = userBookMarkService.count(userId);
        List<PageLink> pageLinks = PageUtils.createPageLinks(page, size, total);

        model.addAttribute("list", list);
        model.addAttribute("pageLinks", pageLinks);
        model.addAttribute("currentPage", page);

        return "bookmarkList/bookmarkList"; // JSP 경로
    }
}

