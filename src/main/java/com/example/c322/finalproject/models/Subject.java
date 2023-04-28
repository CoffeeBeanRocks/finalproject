package com.example.c322.finalproject.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subject {

    private Map<String, Observer> observers;

    public Subject() {
        observers = new HashMap<>();
    }

    public void subscribe(String email, Observer o) {
        observers.put(email, o);
    }

    public void unsubscribe(String email) {
        observers.remove(email);
    }

    public void notify(Notification notification) {
        for (Map.Entry<String, Observer> entry : observers.entrySet()) {
            entry.getValue().update(notification);
        }
    }

    public Map<String, Observer> getObservers() {
        return observers;
    }

    public void setObservers(Map<String, Observer> observers) {
        this.observers = observers;
    }
}
