package com._1.musicrm.controller;

import com._1.musicrm.model.User;
import com._1.musicrm.service.UserService;
import com._1.musicrm.exception.DuplicateEmailException;
import com._1.musicrm.util.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // 允许跨域访问
public class UserRegistrationApiController {

    private final UserService userService;

    public UserRegistrationApiController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        // 输入验证错误处理
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Invalid value"
                    ));
            return ResponseEntity.badRequest().body(errors);
        }

        // 业务逻辑处理
        try {
            User registeredUser = userService.registerUser(user);
            Map<String, Object> response = ResponseUtils.createResponse("message", "注册成功");
            response.put("email", registeredUser.getEmail());
            return ResponseEntity.ok(response);
        } catch (DuplicateEmailException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ResponseUtils.createResponse("error", ex.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.createResponse("error", "注册失败，请稍后重试"));
        }
    }

    /**
     * 上传头像
     */
    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("avatar") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseUtils.createResponse("error", "请上传有效的图片"));
        }

        try {
            String fileUrl = userService.uploadAvatar(file); // 调用 Service 层处理头像上传
            return ResponseEntity.ok(ResponseUtils.createResponse("message", "头像上传成功", "url", fileUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.createResponse("error", "头像上传失败，请稍后重试"));
        }
    }

    /**
     * 更新个人信息
     */
    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody User user) {
        try {
            User updatedUser = userService.updateUserProfile(user);
            return ResponseEntity.ok(ResponseUtils.createResponse("message", "个人信息更新成功", "user", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.createResponse("error", "更新个人信息失败，请稍后重试"));
        }
    }

    /**
     * 更改密码
     */
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
        @RequestParam("email") String email,  // 需要提供 email 参数
        @RequestParam("currentPassword") String currentPassword,
        @RequestParam("newPassword") String newPassword) {
        try {
            userService.changePassword(email, currentPassword, newPassword);
            return ResponseEntity.ok(ResponseUtils.createResponse("message", "密码修改成功"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.createResponse("error", "参数错误：" + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.createResponse("error", "密码修改失败，请稍后重试"));
        }
    }
}