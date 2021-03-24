package com.sc.auth.repo;

import com.sc.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByUserName(String userName);
}
