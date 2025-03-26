package com._1.musicrm.service;

import com._1.musicrm.exception.DuplicateEmailException;
import com._1.musicrm.model.User;
import com._1.musicrm.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 直接保存密码（不加密）
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEmailException("邮箱已被注册");
        }
        return userRepository.save(user); // 密码明文存储
    }
}