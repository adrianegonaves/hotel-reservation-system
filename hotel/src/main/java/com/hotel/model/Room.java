package com.hotel.model;

import com.hotel.observer.RoomObserver;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private int number;
    private RoomStatus status = RoomStatus.AVAILABLE;
    private List<RoomObserver> observers = new ArrayList<>();

    public Room(int number) {
        this.number = number;
    }

    public int getNumber() { return number; }
    public RoomStatus getStatus() { return status; }
    
    public boolean isAvailable() {
        return status == RoomStatus.AVAILABLE;
    }
    
    public void addObserver(RoomObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(RoomObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (RoomObserver observer : observers) {
            observer.onRoomStatusChanged(this);
        }
    }
    
    public void occupy() { 
        status = RoomStatus.OCCUPIED; 
        notifyObservers();
    }

    public void free() { 
        status = RoomStatus.CLEANING; 
        notifyObservers();
    }

    public void cleanFinished() { 
        status = RoomStatus.AVAILABLE; 
        notifyObservers();
    }

    public void setUnderMaintenance() {
        status = RoomStatus.MAINTENANCE;
        notifyObservers();
    }
}