package com.example.c322.finalproject.repositories;

import com.example.c322.finalproject.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findBySendEmail(boolean sendEmail);
}
