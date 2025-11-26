package com.ssr.newskuku.domain.login.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.error("OAuth2 인증 실패: {}", exception.getMessage());
        log.info("리다이렉트 URL: /login?error=true&message={}", exception.getMessage());

        if (response.isCommitted()) {
            log.warn("Response already committed. Cannot redirect.");
            return;
        }

        getRedirectStrategy().sendRedirect(request, response,
                "/login?error=true&message=" + exception.getMessage());
    }
}