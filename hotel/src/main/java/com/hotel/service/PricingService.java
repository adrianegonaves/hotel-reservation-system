package com.hotel.service;

import com.hotel.strategy.PricingStrategy;

public class PricingService {
    private PricingStrategy strategy;

    public PricingService(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculate(int nights) {
        return strategy.calculatePrice(nights);
    }
}