package com.example.c322.finalproject.repositories;

import com.example.c322.finalproject.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
