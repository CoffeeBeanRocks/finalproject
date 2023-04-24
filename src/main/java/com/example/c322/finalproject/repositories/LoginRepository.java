package com.example.c322.finalproject.repositories;

import com.example.c322.finalproject.models.Account;
import com.example.c322.finalproject.models.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginRepository extends JpaRepository<Login, Integer> {
    List<Login> findByEmail(String email);
    List<Login> findByAccount(Account account);
}
