package com.example.c322.finalproject.models;

public class TransferObserver extends Observer{

    @Override
    public void update(String email) {
        //TODO: Send email!!
        System.out.println("Email sent to " + email + " your account has been updated!" );
    }
}
