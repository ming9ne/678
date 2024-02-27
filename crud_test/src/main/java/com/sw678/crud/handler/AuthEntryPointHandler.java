package com.sw678.crud.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class AuthEntryPointHandler implements AuthenticationEntryPoint {
    String errorMessage;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        System.out.println("인증 없이 url로 페이지 접근 불가");
        errorMessage = "로그인 먼저 진행해주세요.";
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        response.sendRedirect(request.getContextPath() + "/login?error=true&exception=" + errorMessage);
    }
}
