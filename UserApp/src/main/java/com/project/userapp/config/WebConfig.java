package com.project.userapp.config;

import com.project.userapp.util.interceptor.FirstVisitInterceptor;
import com.project.userapp.util.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private FirstVisitInterceptor firstVisitInterceptor;
    
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Value("${com.project.userapp.upload.path}")
    private String uploadPath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 처음 접속할때 동작하는 인터셉터
        registry.addInterceptor(firstVisitInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/loading","/css/**", "/js/**", "/img/**", "/favicon.ico");



        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") // 모든 경로에 적용
                .excludePathPatterns(
                        "/loading",
                    "/user/*", // 유저로그인관련
                    "/kinder", // 유치원리스트가져오기
                    "/css/**", "/js/**", "/img/**", "/favicon.ico"); // 정적 리소스 제외); // 리다이렉트 대상은 제외!

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///" + uploadPath + "/");
    }


}
