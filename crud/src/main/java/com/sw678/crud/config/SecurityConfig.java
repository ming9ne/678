package com.sw678.crud.config;

import com.sw678.crud.service.oauth.PrincipleOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private PrincipleOauth2UserService principleOauth2UserService;


    @Autowired
    public SecurityConfig(PrincipleOauth2UserService principleOauth2UserService){
        this.principleOauth2UserService = principleOauth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.
                csrf(AbstractHttpConfigurer::disable)       //다른 도메인에서 API 호출을 안 막음. Rest Api -> 브라우저 통해 request 받아서 꺼도 됨.
                .cors(AbstractHttpConfigurer::disable);
     //           .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.
                authorizeHttpRequests( auth -> auth
                        .requestMatchers("/user").authenticated()
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().permitAll()
                );

        http
                .formLogin(formLogin -> formLogin
                                .loginPage("/loginForm")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/board/list")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .permitAll()
                );
        http
                .oauth2Login(oauth -> oauth
                        .loginPage("/loginForm")
                        .defaultSuccessUrl("/board/list")
                        .failureUrl("/login?error=true")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(principleOauth2UserService))
                );
        http
                .logout(logout -> logout
                        .logoutSuccessUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("remember-me")
                );

        return http.build();

    }
}
