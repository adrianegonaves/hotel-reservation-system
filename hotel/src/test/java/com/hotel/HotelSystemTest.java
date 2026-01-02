package com.hotel;

import com.hotel.model.*;
import com.hotel.service.*;
import com.hotel.strategy.*;
import com.hotel.factory.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class HotelSystemTest {

    @Test void guestValidName() { assertEquals("Ana", new Guest("Ana").getName()); }
    
    @Test void guestInvalidName() { 
        assertThrows(IllegalArgumentException.class, () -> new Guest("")); 
    }

    @Test void roomInitialState() { assertTrue(new Room(1).isAvailable()); }

    @Test void roomOccupy() { 
        Room r = new Room(1); 
        r.occupy();
        assertFalse(r.isAvailable()); 
    }

    @Test void roomFree() { 
        Room r = new Room(1); 
        r.occupy(); 
        r.free();
        assertEquals(RoomStatus.CLEANING, r.getStatus()); 
    }

    @Test void roomCleaningToAvailable() {
        Room r = new Room(1);
        r.free();
        r.cleanFinished(); 
        assertTrue(r.isAvailable());
    }

    @Test void reservationValid() {
        Reservation r = new Reservation(new Guest("Ana"), new Room(1), 2);
        assertEquals(2, r.getNights());
    }

    @Test void reservationInvalidNights() {
        assertThrows(IllegalArgumentException.class, () -> new Reservation(new Guest("Ana"), new Room(1), 0));
    }

    @Test void standardPricing() {
        PricingService p = new PricingService(new StandardPricingStrategy());
        assertEquals(300, p.calculate(3));
    }

    @Test void seasonalPricing() {
        PricingService p = new PricingService(new SeasonalPricingStrategy());
        assertEquals(450, p.calculate(3));
    }

    @Test void reservationServiceSuccess() {
        ReservationService rs = new ReservationService();
        Room room = new Room(1);
        Reservation r = rs.reserve(new Guest("Ana"), room, 2);
        assertEquals(RoomStatus.OCCUPIED, room.getStatus());
    }

    @Test void reservationServiceFail() {
        ReservationService rs = new ReservationService();
        Room room = new Room(1);
        room.occupy();
        assertThrows(IllegalStateException.class, () -> rs.reserve(new Guest("Ana"), room, 2));
    }

    @Test void invoiceCreation() {
        BillingService b = new BillingService();
        assertEquals(500, b.generate(500).getTotal());
    }

    @Test void invoiceInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Invoice(-1));
    }
}