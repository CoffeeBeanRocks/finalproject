package com.example.c322.finalproject.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subject {

    private List<Observer> observers;

    public Subject() {
        observers = new ArrayList<>();
    }

    public void subscribe(Observer o) {
        observers.add(o);
    }

    public void unsubscribe(Observer o) {
        observers.remove(o);
    }

    public void notify(Notification notification) {
        for (Observer observer : observers)
            observer.update(notification);
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }
}
