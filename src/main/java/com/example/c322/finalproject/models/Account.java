package com.example.c322.finalproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account implements AccountService{
    @Id
    private int id;

    private double balance;

    private boolean sendEmail;

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

    public void addAmount(double amount) {
        balance += amount;
    }

    public void subtractAmount(double amount) {
        balance -= amount;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    @Override
    public void transferMoney(Account recipient, double amount) {
        subtractAmount(amount);
        recipient.addAmount(amount);
    }
}
