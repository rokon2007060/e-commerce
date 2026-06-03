package com.example.e_commerce_app;

import java.util.ArrayList;
import java.util.List;

public class UserObservable {
    private final List<UserObserver> observers = new ArrayList<>();

    public void addObserver(UserObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(UserObserver observer) {
        observers.remove(observer);
    }

    public void notifyUserAdded(User newUser) {
        for (UserObserver observer : observers) {
            observer.onUserAdded(newUser);
        }
    }
}

