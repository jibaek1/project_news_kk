package com.ssr.newskuku.domain.bookmark.controller;

import com.ssr.newskuku.domain.bookmark.BookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
@RequestMapping
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @PostMapping("/toggle")
    @ResponseBody
    public boolean toggle(@RequestParam Long newsId,
                          @RequestParam Long userId) {

        return bookMarkService.toggle(userId, newsId);
    }

}
