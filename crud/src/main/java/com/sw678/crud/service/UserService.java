package com.sw678.crud.service;

import com.sw678.crud.model.dto.SigninDto;
import com.sw678.crud.model.dto.SignupDto;
import com.sw678.crud.model.entity.Role;
import com.sw678.crud.model.entity.User;
import com.sw678.crud.model.entity.socialuser.UserDetail;
import com.sw678.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signup(SignupDto signupDto) {
        String encodePassword = bCryptPasswordEncoder.encode(signupDto.getPassword());
        signupDto.setPassword(encodePassword);
        User user = signupDto.toEntity();

        userRepository.save(user);
    }

    public User login(SigninDto signinDto) {
        String username = signinDto.getUsername();
        String password = signinDto.getPassword();

        System.out.println(username);
        System.out.println(password);
        System.out.println(bCryptPasswordEncoder.encode(password));
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.info("Not Exists User");
            return null;
        }
        System.out.println(user);
        return (bCryptPasswordEncoder.matches(password, user.getPassword())) ? user : null;
    }

    //    public SignupDto getCurrentUserDetails() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            // 사용자 정보가 UserDetails를 구현한 객체에 저장되어 있다고 가정
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//            // 사용자 정보를 UserDetails 구현체에서 가져오기
//            String username = userDetails.getUsername();
//
//
//
//            // 사용자 정보 구조에 따라 이 부분을 사용자 이메일, 권한 등으로 커스터마이징할 수 있습니다.
//            SignupDto userDetail = new SignupDto();
//            userDetail.setUsername(username);
//
//
//            return userDetail;
//        }
//
//        // 로그인하지 않은 경우에 대한 처리
//        return null;
//    }
    public SignupDto getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // 사용자 정보가 UserDetails를 구현한 객체에 저장되어 있다고 가정
            UserDetail userDetail = (UserDetail) authentication.getPrincipal();

            // UserDetail 객체에서 사용자 정보 가져오기
            String username = userDetail.getUsername();
            String nickname = userDetail.getNickname();
            String email = userDetail.getEmail();
            Role role = userDetail.getRole();
            String createDate = userDetail.getCreate_date();

            // 사용자 정보 구조에 따라 이 부분을 사용자 이메일, 권한 등으로 커스터마이징할 수 있습니다.
            SignupDto signupDto = new SignupDto();
            signupDto.setUsername(username);
            signupDto.setNickname(nickname);
            signupDto.setEmail(email);
            signupDto.setRole(role);
            signupDto.setCreateDate(LocalDateTime.parse(createDate));

            return signupDto;
        }

        // 로그인하지 않은 경우에 대한 처리
        return null;
    }


}
