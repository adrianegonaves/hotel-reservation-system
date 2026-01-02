package com.hotel.strategy;

public class SeasonalPricingStrategy implements PricingStrategy {
    public double calculatePrice(int nights) {
        return nights * 150;
    }
}