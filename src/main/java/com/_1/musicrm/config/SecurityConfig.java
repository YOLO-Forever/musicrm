package com._1.musicrm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/register").permitAll() // 允许访问注册页面
                .requestMatchers("/api/auth/register").permitAll() // 允许访问注册 API
                .anyRequest().authenticated() // 其他请求需要登录
            )
            .formLogin(login -> login
                .loginPage("/auth/login") // 指定登录页面（可修改为你的路径）
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }
    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // 不进行密码加密
    }
}