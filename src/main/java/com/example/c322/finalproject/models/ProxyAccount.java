package com.example.c322.finalproject.models;

import com.example.c322.finalproject.repositories.TransactionRepository;
import jakarta.persistence.*;

public class ProxyAccount implements AccountService{

    private Account sender;
    private TransactionRepository transactionRepository;

    public ProxyAccount(Account sender, TransactionRepository transactionRepository) {
        this.sender = sender;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void transferMoney(Account recipient, double amount) {
        if(sender.getBalance() < amount)
            throw new IllegalStateException("Insufficient Funds!");

        Transaction transaction = new Transaction();
        transaction.setSenderId(sender.getId());
        transaction.setRecipientId(recipient.getId());
        transaction.setAmount(amount);

        transactionRepository.save(transaction);

        sender.transferMoney(recipient, amount);
    }
}
