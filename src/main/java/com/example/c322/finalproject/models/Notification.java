package com.example.c322.finalproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Notification {
    @Id
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
