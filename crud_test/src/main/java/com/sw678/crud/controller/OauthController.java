package com.sw678.crud.controller;

import com.sw678.crud.model.entity.user.UserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/oauth2/code")
public class OauthController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping("/google")
    public String googleLoginInfo(@AuthenticationPrincipal UserDetail userDetail, Model model){
        log.info("google Login Info!");
        return userDetail.toString();
    }

}
