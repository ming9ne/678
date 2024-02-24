package com.sw678.crud.service.oauth;

import com.sw678.crud.model.entity.User;
import com.sw678.crud.model.entity.socialuser.UserDetail;
import com.sw678.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipleSiteUserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user!=null) {
            user.setNickname(user.getUsername());
            System.out.println("일반.user = " + user);
            return new UserDetail(user);
        }
        return null;
    }
}
