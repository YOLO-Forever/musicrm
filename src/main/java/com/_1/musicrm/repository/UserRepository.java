// src/main/java/com/musicrm/repository/UserRepository.java
package com._1.musicrm.repository;

import com._1.musicrm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}