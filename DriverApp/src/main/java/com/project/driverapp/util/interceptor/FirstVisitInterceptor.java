package com.project.driverapp.util.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component // webConfig에서 bean으로 등록안해도됨 (자동 빈 등록)
public class FirstVisitInterceptor implements HandlerInterceptor {
    // 첫 화면시 무조건 로딩화면
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();
        String uri = request.getRequestURI();

        System.out.println("firstVisit: " + session.getAttribute("firstVisit"));

        // 세션에 방문 여부가 없다면
        if (session.getAttribute("firstVisit") == null) {
            session.setAttribute("firstVisit", true);

            // 로딩 페이지 자체는 리다이렉트하지 않도록 예외 처리
            if (!uri.equals("/loading")) {
                response.sendRedirect("/loading");
                return false;
            }
        }

        return true; // 이미 방문했으면 계속 진행
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("컨트롤러 실행 후 인터셉터 동작");
    }
}
