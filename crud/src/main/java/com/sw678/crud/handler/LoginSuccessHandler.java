package com.sw678.crud.handler;

import com.sw678.crud.model.entity.socialuser.UserDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetail user = (UserDetail) authentication.getPrincipal();
        System.out.println(user);
        request.setAttribute("nickname", user.getUser().getNickname());
        response.sendRedirect("/loginSuccess");
    }
}
