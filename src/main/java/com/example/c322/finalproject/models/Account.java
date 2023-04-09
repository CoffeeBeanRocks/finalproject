package com.example.c322.finalproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {
    @Id
    private int id;

    private double balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // needs to edit balance from this.account and find new account in database to add funds
    public void transferMoney(Account account, double amount) {
        this.balance -= amount;
        account.setBalance(account.getBalance()+amount);
    }
}
