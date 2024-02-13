package com.sw678.crud.service;

import com.sw678.crud.model.dto.SigninDto;
import com.sw678.crud.model.dto.SignupDto;
import com.sw678.crud.model.entity.User;
import com.sw678.crud.model.entity.socialuser.UserDetail;
import com.sw678.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signup(SignupDto signupDto){
        String encodePassword = bCryptPasswordEncoder.encode(signupDto.getPassword());
        signupDto.setPassword(encodePassword);
        User user = signupDto.toEntity();

        userRepository.save(user);
    }

    public User login(SigninDto signinDto){
        String username = signinDto.getUsername();
        String password = signinDto.getPassword();

        System.out.println(username);
        System.out.println(password);
        System.out.println(bCryptPasswordEncoder.encode(password));
        User user = userRepository.findByUsername(username);
        if(user == null) {
            log.info("Not Exists User");
            return null;
        }
        System.out.println(user);
        return (bCryptPasswordEncoder.matches(password, user.getPassword())) ? user : null;
    }

}
