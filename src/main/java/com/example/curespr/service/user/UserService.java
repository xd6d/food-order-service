package com.example.curespr.service.user;

import com.example.curespr.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findByUsername(String username);

    User createUser(String username, String password);
}
