package com.hotel.strategy;

public class StandardPricingStrategy implements PricingStrategy {
    public double calculatePrice(int nights) {
        return nights * 100;
    }
}