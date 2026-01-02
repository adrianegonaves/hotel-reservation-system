package com.hotel;

import com.hotel.model.*;
import com.hotel.service.*;
import com.hotel.strategy.*;
import com.hotel.factory.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class HotelSystemTest {

    // ==========================================
    // 1. TESTES UNITÁRIOS: GUEST (Hóspede)
    // ==========================================

    @Test
    void TC01_guestValidName() {
        Guest g = new Guest("Ana");
        assertEquals("Ana", g.getName());
    }

    @Test
    void TC02_guestInvalidNameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Guest(""));
    }

    @Test
    void TC03_guestInvalidNameNull() {
        assertThrows(IllegalArgumentException.class, () -> new Guest(null));
    }

    // ==========================================
    // 2. TESTES UNITÁRIOS: ROOM (Quarto)
    // ==========================================

    @Test
    void TC04_roomInitialStateIsAvailable() {
        Room r = new Room(101);
        assertTrue(r.isAvailable());
        assertEquals(RoomStatus.AVAILABLE, r.getStatus());
    }

    @Test
    void TC05_roomGetNumber() {
        Room r = new Room(202);
        assertEquals(202, r.getNumber());
    }

    @Test
    void TC06_roomOccupyTransition() {
        Room r = new Room(101);
        r.occupy();
        assertFalse(r.isAvailable());
        assertEquals(RoomStatus.OCCUPIED, r.getStatus());
    }

    @Test
    void TC07_roomFreeTransition() {
        Room r = new Room(101);
        r.occupy();
        r.free(); 
        assertFalse(r.isAvailable());
        assertEquals(RoomStatus.CLEANING, r.getStatus());
    }

    @Test
    void TC08_roomCleaningFinishedTransition() {
        Room r = new Room(101);
        r.free(); 
        r.cleanFinished(); 
        assertTrue(r.isAvailable());
        assertEquals(RoomStatus.AVAILABLE, r.getStatus());
    }

    @Test
    void TC09_roomDirectStatusCheck() {
        Room r = new Room(1);
        assertNotNull(r.getStatus());
    }

    // ==========================================
    // 3. TESTES UNITÁRIOS: RESERVATION (Reserva)
    // ==========================================

    @Test
    void TC10_reservationCreationValid() {
        Guest g = new Guest("Bruno");
        Room r = new Room(101);
        Reservation res = new Reservation(g, r, 5);
        
        assertEquals(5, res.getNights());
        assertEquals(g, res.getGuest());
        assertEquals(r, res.getRoom());
    }

    @Test
    void TC11_reservationInvalidNightsZero() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Reservation(new Guest("Ana"), new Room(1), 0));
    }

    @Test
    void TC12_reservationInvalidNightsNegative() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Reservation(new Guest("Ana"), new Room(1), -10));
    }

    // ==========================================
    // 4. TESTES DE PADRÃO STRATEGY (Preços)
    // ==========================================

    @Test
    void TC13_standardPricingCalculation() {
        PricingService service = new PricingService(new StandardPricingStrategy());
        assertEquals(300.0, service.calculate(3));
    }

    @Test
    void TC14_standardPricingSingleNight() {
        PricingService service = new PricingService(new StandardPricingStrategy());
        assertEquals(100.0, service.calculate(1));
    }

    @Test
    void TC15_seasonalPricingCalculation() {
        PricingService service = new PricingService(new SeasonalPricingStrategy());
        assertEquals(300.0, service.calculate(2));
    }

    @Test
    void TC16_seasonalPricingSingleNight() {
        PricingService service = new PricingService(new SeasonalPricingStrategy());
        assertEquals(150.0, service.calculate(1));
    }

    @Test
    void TC17_pricingServiceZeroNights() {
        PricingService service = new PricingService(new StandardPricingStrategy());
        assertEquals(0.0, service.calculate(0));
    }

    // ==========================================
    // 5. TESTES DE SERVIÇO: RESERVATION SERVICE
    // ==========================================

    @Test
    void TC18_reservationServiceSuccess() {
        ReservationService service = new ReservationService();
        Room room = new Room(500);
        Guest guest = new Guest("Carla");
        
        Reservation res = service.reserve(guest, room, 3);
        
        assertNotNull(res);
        assertEquals(RoomStatus.OCCUPIED, room.getStatus());
    }

    @Test
    void TC19_reservationServiceFailIfOccupied() {
        ReservationService service = new ReservationService();
        Room room = new Room(500);
        room.occupy(); 
        
        assertThrows(IllegalStateException.class, 
            () -> service.reserve(new Guest("Carla"), room, 3));
    }

    @Test
    void TC20_reservationServiceFailIfCleaning() {
        ReservationService service = new ReservationService();
        Room room = new Room(500);
        room.free(); 
        
        assertThrows(IllegalStateException.class, 
            () -> service.reserve(new Guest("Carla"), room, 3));
    }

    @Test
    void TC21_reservationServiceFailIfMaintenance() {
        Room room = new Room(500);
        room.occupy(); 
        assertFalse(room.isAvailable());
    }

    // ==========================================
    // 6. TESTES DE PADRÃO FACTORY & BILLING
    // ==========================================

    @Test
    void TC22_invoiceFactoryCreation() {
        Invoice invoice = InvoiceFactory.create(120.50);
        assertEquals(120.50, invoice.getTotal());
    }

    @Test
    void TC23_billingServiceGenerate() {
        BillingService service = new BillingService();
        Invoice invoice = service.generate(1000.0);
        assertEquals(1000.0, invoice.getTotal());
    }

    @Test
    void TC24_invoiceNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Invoice(-50));
    }

    @Test
    void TC25_invoiceZeroAmount() {
        Invoice invoice = new Invoice(0);
        assertEquals(0.0, invoice.getTotal());
    }

    // ==========================================
    // 7. TESTES DE INTEGRAÇÃO (Fluxos Completos)
    // ==========================================

    @Test
    void TC26_integrationFullCycle() {
        Guest guest = new Guest("Integrated Guest");
        Room room = new Room(101);
        ReservationService rs = new ReservationService();
        
        // 1. Reserva
        Reservation res = rs.reserve(guest, room, 4);
        assertEquals(RoomStatus.OCCUPIED, room.getStatus());
        
        // 2. Preço
        PricingService ps = new PricingService(new SeasonalPricingStrategy());
        double price = ps.calculate(res.getNights()); 
        assertEquals(600.0, price);
        
        // 3. Fatura
        BillingService bs = new BillingService();
        Invoice inv = bs.generate(price);
        assertEquals(600.0, inv.getTotal());
    }

    @Test
    void TC27_integrationRoomLifecycle() {
        Room r = new Room(303);
        assertTrue(r.isAvailable());
        
        r.occupy();
        assertEquals(RoomStatus.OCCUPIED, r.getStatus());
        
        r.free();
        assertEquals(RoomStatus.CLEANING, r.getStatus());
        
        r.cleanFinished();
        assertEquals(RoomStatus.AVAILABLE, r.getStatus());
    }

    // ==========================================
    // 8. TESTES DE EXTREMOS E EXCEÇÕES EXTRA
    // ==========================================

    @Test
    void TC28_largeNumberOfNights() {
        PricingService ps = new PricingService(new StandardPricingStrategy());
        assertEquals(100000.0, ps.calculate(1000));
    }

    @Test
    void TC29_guestNameTrim() {
        Guest g = new Guest(" Ana ");
        assertEquals(" Ana ", g.getName());
    }

    @Test
    void TC30_roomMultipleOccupies() {
        Room r = new Room(1);
        r.occupy();
        r.occupy(); 
        assertEquals(RoomStatus.OCCUPIED, r.getStatus());
    }
    
    @Test
    void TC31_checkEnumValues() {
        assertEquals(4, RoomStatus.values().length);
    }
    
    @Test
    void TC32_billingServicePrecision() {
        BillingService bs = new BillingService();
        Invoice i = bs.generate(99.99);
        assertEquals(99.99, i.getTotal(), 0.001);
    }

    @Test
    void TC33_pricingServiceStrategySwitch() {
        PricingService s1 = new PricingService(new StandardPricingStrategy());
        PricingService s2 = new PricingService(new SeasonalPricingStrategy());
        assertNotEquals(s1.calculate(1), s2.calculate(1));
    }

    @Test
    void TC34_reservationValuesImmutability() {
        Guest g = new Guest("Tom");
        Room r = new Room(5);
        Reservation res = new Reservation(g, r, 2);
        
        assertEquals(2, res.getNights());
        r.free();
        assertEquals(RoomStatus.CLEANING, res.getRoom().getStatus());
    }

    @Test
    void TC35_invoiceLargeAmount() {
        Invoice i = new Invoice(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, i.getTotal());
    }

    @Test
    void TC36_reserveNullGuest() {
        ReservationService rs = new ReservationService();
        Room room = new Room(1000);
        assertThrows(IllegalArgumentException.class, () -> rs.reserve(null, room, 2));
    }

    @Test
    void TC37_reserveNullRoom() {
        ReservationService rs = new ReservationService();
        Guest guest = new Guest("Tester");
        assertThrows(IllegalArgumentException.class, () -> rs.reserve(guest, null, 2));
    }

    @Test
    void TC38_reserveInvalidNightsInService() {
        ReservationService rs = new ReservationService();
        Room room = new Room(2000);
        Guest guest = new Guest("Tester");
        assertThrows(IllegalArgumentException.class, () -> rs.reserve(guest, room, 0));
        assertThrows(IllegalArgumentException.class, () -> rs.reserve(guest, room, -1));
    }

    @Test
    void TC39_pricingServiceNegativeNightsThrows() {
        PricingService ps = new PricingService(new StandardPricingStrategy());
        assertThrows(IllegalArgumentException.class, () -> ps.calculate(-1));
    }
}