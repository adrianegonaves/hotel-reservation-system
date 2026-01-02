package com.hotel.model;

public class Invoice {
    private double total;

    public Invoice(double total) {
        if (total < 0) throw new IllegalArgumentException();
        this.total = total;
    }

    public double getTotal() { return total; }
}