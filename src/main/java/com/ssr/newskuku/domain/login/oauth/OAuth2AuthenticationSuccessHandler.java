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
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 추가 정보가 필요한 경우
        if (oAuth2User.getAttributes().containsKey("needsAdditionalInfo") &&
                (boolean) oAuth2User.getAttributes().get("needsAdditionalInfo")) {

            Long userId = (Long) oAuth2User.getAttributes().get("userId");
            String targetUrl = UriComponentsBuilder.fromUriString("/user/additional-info")
                    .queryParam("userId", userId)
                    .build().toUriString();

            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        } else {
            // 메인 페이지로 리다이렉트
            getRedirectStrategy().sendRedirect(request, response, "/");
        }
    }
}