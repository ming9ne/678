package com.sw678.crud.repository;

import com.sw678.crud.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //Optional<User> findByEmail(String email); // email을 통해 이미 있는 유저인지 체크
    public User findByUserName(String username);
}
