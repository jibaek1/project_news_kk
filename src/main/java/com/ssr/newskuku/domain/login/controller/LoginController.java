package com.ssr.newskuku.domain.login.controller;

import com.ssr.newskuku.domain.login.UserCategory;
import com.ssr.newskuku.domain.login.UserInfo;
import com.ssr.newskuku.domain.login.UserService;
import com.ssr.newskuku.domain.login.dto.UserRequest;
import com.ssr.newskuku.domain.login.dto.UserResponse;
import com.ssr.newskuku.domain.news.NewsService;
import com.ssr.newskuku.domain.news.dto.NewsResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final NewsService newsService;

    @GetMapping
    public String index(HttpSession session,
                        Model model,
                        @SessionAttribute(value = "loginUser", required = false) UserInfo loginUser) {

        // 인기뉴스(4개)
        List<NewsResponse.FindAll> popularNews = newsService.findPopularNews(4);
        model.addAttribute("popularNewsList", popularNews);



        // 관심사 기반 뉴스
        if (loginUser != null) {

            // 로그인 사용자 ID 가져오기
            Long userId = loginUser.getUserId();

            UserInfo userInfo = userService.getUserInfo(userId);

            List<String> categories = userInfo.getCategories(); // List<String>

            List<NewsResponse.FindAll> interestNews =
                    newsService.findNewsByCategories(categories, 4);

            model.addAttribute("interestNewsList", interestNews);

        } else {
            // 비로그인 사용자 → 관심사 리스트는 빈 값
            model.addAttribute("interestNewsList", List.of());
        }

        return "index";
    }

    @GetMapping("/auth/login")
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

    /**
     * 추가 정보 입력 페이지 (GET)
     */
    @GetMapping("regist")
    public String registForm(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String oauthNickname,
            @RequestParam(required = false) String oauthGender,
            @RequestParam(required = false) String oauthBirthday,
            @RequestParam(required = false) String oauthBirthyear,
            Model model) {

        log.info("추가 정보 입력 페이지: userId={}", userId);

        if (userId != null) {
            model.addAttribute("userId", userId);

            // OAuth에서 받은 정보를 모델에 추가
            if (oauthNickname != null) {
                model.addAttribute("nickname", oauthNickname);
            }
            if (oauthGender != null) {
                // M -> MALE, F -> FEMALE 변환
                String gender = "M".equals(oauthGender) ? "MALE" : "FEMALE";
                model.addAttribute("gender", gender);
            }
            if (oauthBirthyear != null && oauthBirthday != null) {
                // 1990-01-15 형식으로 조합
                String birthDate = oauthBirthyear + "-" + oauthBirthday;
                model.addAttribute("birthDate", birthDate);
            }
        }

        return "auth/regist";
    }

    /**
     * 추가 정보 저장 (POST)
     */
    @PostMapping("regist")
    public String saveAdditionalInfo(@RequestParam Long userId,
                                     @ModelAttribute UserRequest.AdditionalInfo request,
                                     HttpSession session) {
        log.info("추가 정보 저장: userId={}, request={}", userId, request);

        userService.updateAdditionalInfo(userId, request);

        UserInfo info = userService.getUserInfo(userId);
        session.setAttribute("loginUser", info);
        return "redirect:/";
    }
}