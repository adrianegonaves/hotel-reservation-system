package com.hotel.model;

public class Room {
    private int number;
    private RoomStatus status = RoomStatus.AVAILABLE;

    public Room(int number) {
        this.number = number;
    }

    public int getNumber() { return number; }
    public RoomStatus getStatus() { return status; }
    
    public boolean isAvailable() {
        return status == RoomStatus.AVAILABLE;
    }
    
    public void occupy() { status = RoomStatus.OCCUPIED; }
    public void free() { status = RoomStatus.CLEANING; }
    public void cleanFinished() { status = RoomStatus.AVAILABLE; }
}