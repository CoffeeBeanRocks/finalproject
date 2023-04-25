package com.example.c322.finalproject.repositories;

import com.example.c322.finalproject.models.Account;
import com.example.c322.finalproject.models.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Integer> {
    Optional<Login> findByEmail(String email);
    Optional<Login> findByAccount(Account account);
}
