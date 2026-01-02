package com.hotel.factory;

import com.hotel.model.Invoice;

public class InvoiceFactory {
    public static Invoice create(double amount) {
        return new Invoice(amount);
    }
}