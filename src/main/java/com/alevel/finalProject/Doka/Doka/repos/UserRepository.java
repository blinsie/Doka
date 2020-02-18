package com.alevel.finalProject.Doka.Doka.repos;

import com.alevel.finalProject.Doka.Doka.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}