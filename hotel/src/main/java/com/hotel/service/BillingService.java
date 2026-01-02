package com.hotel.service;

import com.hotel.factory.InvoiceFactory;
import com.hotel.model.Invoice;

public class BillingService {
    public Invoice generate(double amount) {
        return InvoiceFactory.create(amount);
    }
}