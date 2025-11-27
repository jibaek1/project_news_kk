package com.ssr.newskuku.domain.login.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("auth/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "message", required = false) String message,
            Model model) {

        log.info("로그인 페이지 접근 - error: {}, message: {}", error, message);

        if (error != null) {
            model.addAttribute("error", true);
            model.addAttribute("message", message);
        }

        return "auth/login";
    }

    @GetMapping("regist")
    public String registForm() {
        return "auth/regist";
    }
}
