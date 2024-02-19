package com.sw678.crud.service.oauth;

import com.sw678.crud.model.entity.Role;
import com.sw678.crud.model.entity.User;
import com.sw678.crud.model.entity.socialuser.*;
import com.sw678.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipleUserService implements UserDetailsService {


    //private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null){
            user.setNickname(user.getUsername());
            System.out.println("일반.user = " + user);
            return new UserDetail(user);
        }
        return null;
    }
}
