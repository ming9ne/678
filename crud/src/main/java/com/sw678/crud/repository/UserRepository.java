package com.sw678.crud.repository;

import com.sw678.crud.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
    List<User> findByNickname(String nickname);
}
