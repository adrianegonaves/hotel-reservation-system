package com.hotel.model;

public class Guest {
    private String name;

    public Guest(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException();
        this.name = name;
    }
    
    public String getName() { return name; }
}