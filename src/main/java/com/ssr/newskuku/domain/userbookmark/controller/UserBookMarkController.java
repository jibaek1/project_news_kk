package com.ssr.newskuku.domain.userbookmark.controller;

import com.ssr.newskuku.domain.userbookmark.UserBookMarkService;
import com.ssr.newskuku.domain.userbookmark.dto.UserBookMarkResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
                          @RequestParam Long userInfoId, HttpSession httpSession) {

        return userBookMarkService.toggle(userInfoId, newsId);
    }

    @GetMapping("/findAllBookMark")
    @ResponseBody
    public List<UserBookMarkResponse.FindAll> findAllList(@RequestParam Long userInfoId) {

        return userBookMarkService.findAllBookMark(userInfoId);
      }
    }

