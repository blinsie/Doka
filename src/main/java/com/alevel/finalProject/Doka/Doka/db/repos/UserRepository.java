package com.alevel.finalProject.Doka.Doka.db.repos;

import com.alevel.finalProject.Doka.Doka.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    Optional<User> findById(Integer id);
}