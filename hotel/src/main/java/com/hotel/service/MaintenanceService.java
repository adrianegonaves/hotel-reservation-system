package com.hotel.service;

import com.hotel.model.Room;
import com.hotel.model.RoomStatus;
import com.hotel.observer.RoomObserver;

public class MaintenanceService implements RoomObserver {
    @Override
    public void onRoomStatusChanged(Room room) {
        if (room.getStatus() == RoomStatus.MAINTENANCE) {
            System.out.println("ALERTA MANUTENÇÃO: O quarto " + room.getNumber() + " está fora de serviço.");
        }
    }
}