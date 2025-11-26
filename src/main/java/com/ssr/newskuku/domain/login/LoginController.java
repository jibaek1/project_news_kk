package com.ssr.newskuku.domain.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("login")
    public String loginForm() {
        return "/auth/login";
    }

    @GetMapping("regist")
    public String registForm() {
        return "/auth/regist";
    }
}
