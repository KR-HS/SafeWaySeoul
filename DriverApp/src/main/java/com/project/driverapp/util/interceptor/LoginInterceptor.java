package com.project.driverapp.util.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component // webConfig에서 bean으로 등록안해도됨 (자동 빈 등록)
public class LoginInterceptor implements HandlerInterceptor {
    // 로딩후 무조건 로그인화면
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();

        // 세션에 방문 여부가 없다면
        if (session.getAttribute("userMap") == null) {
            // 처음 진입일 경우 특정 페이지로 리다이렉트
            response.sendRedirect("/user/login");
            return false; // 이후 컨트롤러로 안 넘어감
        }

        return true; // 이미 방문했으면 계속 진행
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("컨트롤러 실행 후 인터셉터 동작");
    }
}
