//package com.sw678.crud.service.oauth;
//
//import com.sw678.crud.model.entity.User;
//import com.sw678.crud.model.entity.socialuser.UserDetail;
//import com.sw678.crud.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class PrincipleSiteUserService implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if(user!=null) {
//            user.setNickname(user.getUsername());
//            System.out.println("일반.user = " + user);
//            return new UserDetail(user);
//        }
//        return null;
//    }
//}
package com.sw678.crud.service.oauth;

import com.sw678.crud.model.entity.Role;
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
        if (user != null) {
            user.setNickname(user.getUsername());
            System.out.println("일반.user = " + user);

            // 여기서 UserDetail 생성자에 필요한 인자들을 적절히 제공
            String nickname = user.getNickname(); // 또는 다른 방식으로 nickname을 설정
            String email = user.getEmail(); // 또는 다른 방식으로 email을 설정
            Role role = user.getRole(); // 또는 다른 방식으로 role을 설정
            String create_date = String.valueOf(user.getCreateDate()); // 또는 다른 방식으로 create_date를 설정

            return new UserDetail(user, nickname, email, role, create_date);
        }
        return null;
    }
}
