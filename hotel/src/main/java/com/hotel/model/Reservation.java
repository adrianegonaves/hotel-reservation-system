package com.hotel.model;

public class Reservation {
    private Guest guest;
    private Room room;
    private int nights;

    public Reservation(Guest guest, Room room, int nights) {
        if (nights <= 0) throw new IllegalArgumentException();
        this.guest = guest;
        this.room = room;
        this.nights = nights;
    }

    public Guest getGuest() { return guest; }
    public Room getRoom() { return room; }
    public int getNights() { return nights; }
}