package com.ssr.newskuku.domain.login.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 인증 성공");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        if (response.isCommitted()) {
            log.warn("Response already committed. Cannot redirect.");
            return;
        }

        if (oAuth2User.getAttributes().containsKey("needsAdditionalInfo") &&
                (boolean) oAuth2User.getAttributes().get("needsAdditionalInfo")) {

            Long userId = (Long) oAuth2User.getAttributes().get("userId");

            StringBuilder urlBuilder = new StringBuilder("/regist?userId=").append(userId);

            // OAuth에서 받은 정보도 쿼리 파라미터로 전달 (URL 인코딩)
            if (oAuth2User.getAttributes().containsKey("oauthNickname")) {
                String nickname = (String) oAuth2User.getAttributes().get("oauthNickname");
                if (nickname != null && !nickname.isEmpty()) {
                    urlBuilder.append("&oauthNickname=")
                            .append(URLEncoder.encode(nickname, StandardCharsets.UTF_8));
                }
            }
            if (oAuth2User.getAttributes().containsKey("oauthGender")) {
                String gender = (String) oAuth2User.getAttributes().get("oauthGender");
                if (gender != null && !gender.isEmpty()) {
                    urlBuilder.append("&oauthGender=").append(gender);
                }
            }
            if (oAuth2User.getAttributes().containsKey("oauthBirthday")) {
                String birthday = (String) oAuth2User.getAttributes().get("oauthBirthday");
                if (birthday != null && !birthday.isEmpty()) {
                    urlBuilder.append("&oauthBirthday=").append(birthday);
                }
            }
            if (oAuth2User.getAttributes().containsKey("oauthBirthyear")) {
                String birthyear = (String) oAuth2User.getAttributes().get("oauthBirthyear");
                if (birthyear != null && !birthyear.isEmpty()) {
                    urlBuilder.append("&oauthBirthyear=").append(birthyear);
                }
            }

            String targetUrl = urlBuilder.toString();

            log.info("추가 정보 입력 페이지로 리다이렉트: {}", targetUrl);
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        } else {
            log.info("메인 페이지로 리다이렉트: /");
            getRedirectStrategy().sendRedirect(request, response, "/");
        }
    }
}