package com._1.musicrm.service;

import com._1.musicrm.exception.DuplicateEmailException;
import com._1.musicrm.exception.InvalidPasswordException;
import com._1.musicrm.model.User;
import com._1.musicrm.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String UPLOAD_DIR = "uploads/"; // 本地存储路径

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 用户注册（不加密密码）
     */
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEmailException("邮箱已被注册");
        }
        return userRepository.save(user); // 注意：这里密码是明文存储，建议加密
    }

    /**
     * 上传头像
     */
    public String uploadAvatar(MultipartFile file) {
        try {
            // 目标路径
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 保存文件
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            return "/uploads/" + file.getOriginalFilename(); // 返回存储路径
        } catch (IOException e) {
            throw new RuntimeException("上传失败: " + e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    public User updateUserProfile(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
            () -> new RuntimeException("用户不存在")
        );

        // 更新字段
        existingUser.setUsername(user.getUsername());

        return userRepository.save(existingUser);
    }

    /**
     * 修改密码
     */
    public void changePassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(
            () -> new RuntimeException("用户不存在")
        );

        // 校验当前密码
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new InvalidPasswordException("当前密码错误");
        }

        // 更新密码（加密存储）
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}