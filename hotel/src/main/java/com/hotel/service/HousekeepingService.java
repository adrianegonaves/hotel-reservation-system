package com.hotel.service;

import com.hotel.model.Room;
import com.hotel.model.RoomStatus;
import com.hotel.observer.RoomObserver;

public class HousekeepingService implements RoomObserver {
    @Override
    public void onRoomStatusChanged(Room room) {
        if (room.getStatus() == RoomStatus.CLEANING) {
            System.out.println("ALERTA LIMPEZA: O quarto " + room.getNumber() + " precisa de ser limpo.");
        }
    }
}