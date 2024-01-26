package com.sw678.crud.service;

import com.sw678.crud.model.dto.UserDto;
import com.sw678.crud.model.entity.User;
import com.sw678.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;


    // 중복 회원체크
    public boolean userCheck(UserDto dto) {
        User findUser = userRepository.findByUserName(dto.getUsername());
        if (findUser == null) {
            return true;
        } else {
            return false;
        }
    }

    // 회원가입
    @Transactional
    public Long join(UserDto userDto){
        User user = userDto.toEntity();
        userRepository.save(user);

        return user.getId();
    }

    public UserDto login(UserDto userDto) throws Exception{
        User findUser = userRepository.findByUserName(userDto.getUsername());

        if(findUser != null){
            UserDto findUserDto = new UserDto();
            if(userDto.getPassword().equals(findUserDto.getPassword())){
                return findUserDto;
            }
        }

        return null;
    }


}

