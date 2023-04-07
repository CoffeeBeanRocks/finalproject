package com.example.c322.finalproject.models;

public class AccountModel {
    private int id;
    private int balance;

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }

    // needs to edit balance from this.account and find new account in database to add funds
    public void transferMoney(AccountModel account) {}
}
