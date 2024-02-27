package com.sw678.crud.config;

import com.sw678.crud.handler.AuthEntryPointHandler;
import com.sw678.crud.handler.LoginFailHandler;
import com.sw678.crud.handler.LoginSuccessHandler;
import com.sw678.crud.service.oauth.PrincipleOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig  {
    private final PrincipleOauth2UserService principleOauth2UserService;
    private final LoginFailHandler loginFailHandler;
    private final LoginSuccessHandler loginSuccessHandler;
    private final AuthEntryPointHandler authEntryPointHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.
                csrf(AbstractHttpConfigurer::disable)       //다른 도메인에서 API 호출을 안 막음. Rest Api -> 브라우저 통해 request 받아서 꺼도 됨.
                .cors(AbstractHttpConfigurer::disable);
        http.
                authorizeHttpRequests( auth -> auth
                        .requestMatchers("/login", "/signup","/css/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                );

        http.
                exceptionHandling(auth -> auth
                    .authenticationEntryPoint(authEntryPointHandler) // 허용 url 말고 권한 없이 url페이지 이동 시 작동handler
                );
        http
                .formLogin(formLogin -> formLogin
                        .loginPage("/loginForm")
                        .loginProcessingUrl("/login")
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailHandler)
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                );

        http
                .oauth2Login(oauth -> oauth
                        .loginPage("/loginForm")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(principleOauth2UserService))
                );
        http
                .logout(logout -> logout
                        // 로그아웃 요청을 처리할 URL 설정
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("remember-me")
                );

        return http.build();

    }

//    private AuthenticationFailureHandler failureHandler() {
//        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler("/login?error=로그인+먼저+진행해주세요");
//        failureHandler.setUseForward(true);
//        return failureHandler;
//    }

}


