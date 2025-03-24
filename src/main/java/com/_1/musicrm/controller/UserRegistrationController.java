package com._1.musicrm.User.java

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    // 定义一个路由来获取消息
    @GetMapping("/message")
    public String getMessage(@RequestParam(name = "name", defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    // 定义一个路由来提交表单数据
    @PostMapping("/form")
    public String submitForm(@RequestBody User user) {
        // 处理用户提交的数据
        return "Form submitted with username: " + user.getUsername();
    }

    // 定义一个内部类来表示用户
    public static class User {
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}