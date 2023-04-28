package com.example.c322.finalproject.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Account implements AccountService, Observer{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private double balance;

    private boolean sendEmail;

    @OneToMany
    private List<Notification> notificationList;

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

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @Override
    public void transferMoney(Account recipient, double amount) {
        subtractAmount(amount);
        recipient.addAmount(amount);
    }

    @Override
    public void update(Notification notification) {
        notificationList.add(notification);
    }
}
