package com.sw678.crud.service;

import com.sw678.crud.model.dto.SigninDto;
import com.sw678.crud.model.dto.UserDto;
import com.sw678.crud.model.entity.User;
import com.sw678.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signup(User user){
        userRepository.save(user);
    }

    public User login(SigninDto signinDto){
        String username = signinDto.getUsername();
        String password = signinDto.getPassword();
        return userRepository.findByUsername(username)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
