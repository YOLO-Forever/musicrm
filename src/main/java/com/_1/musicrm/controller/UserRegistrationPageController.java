package com._1.musicrm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class UserRegistrationPageController {

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register"; // 对应 templates/register.html
    }

    @GetMapping("/user-info")
    public String showUserInformationManagementPage() {
        return "UserInformationManagement"; // 对应 templates/UserInformationManagement.html
    }
}