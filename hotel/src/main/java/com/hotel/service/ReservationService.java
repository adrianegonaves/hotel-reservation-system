package com.hotel.service;

import com.hotel.model.*;

public class ReservationService {
    public Reservation reserve(Guest guest, Room room, int nights) {
        if (!room.isAvailable()) throw new IllegalStateException();
        room.occupy();
        return new Reservation(guest, room, nights);
    }
}