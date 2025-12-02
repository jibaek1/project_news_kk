package com.ssr.newskuku._global.config.session;


import com.ssr.newskuku._global.common.Define;
import com.ssr.newskuku._global.errors.adminException.Exception401;
import com.ssr.newskuku.domain.login.UserAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        System.out.println("인터셉터 동작 확인 : " + request.getRequestURL());
        UserAccount sessionUser = (UserAccount)session.getAttribute(Define.LOGIN_SUCCESS);
        if(sessionUser == null){
            throw new Exception401("로그인 먼저 해주세요");
        }
        /*
        Admin sessionUser = (Admin)session.getAttribute(Define.SESSION_USER);
        if(sessionUser == null) {
            throw new AdminException401("로그인 먼저 해주세요");
            //return false;
        }
         */
        return true;
    }
}
