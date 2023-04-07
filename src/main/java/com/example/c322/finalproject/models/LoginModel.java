package com.example.c322.finalproject.models;
import jakarta.persistence.Entity;

public class LoginModel {
    private String email;
    private String password;
    private AccountModel account;

    // getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }
}
