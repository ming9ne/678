package com.sw678.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("your-mail-host"); // 메일 호스트 설정
        mailSender.setPort(587); // 메일 포트 설정
        // 기타 설정 (사용하는 프로토콜, 사용자명, 패스워드 등)

        return mailSender;
    }
}
