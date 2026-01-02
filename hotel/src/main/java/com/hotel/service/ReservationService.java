package com.hotel.service;

import com.hotel.model.*;

public class ReservationService {
    public Reservation reserve(Guest guest, Room room, int nights) {
        if (guest == null) throw new IllegalArgumentException("guest");
        if (room == null) throw new IllegalArgumentException("room");
        if (nights <= 0) throw new IllegalArgumentException("nights");
        if (!room.isAvailable()) throw new IllegalStateException();
        room.occupy();
        return new Reservation(guest, room, nights);
    }
}