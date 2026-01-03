package com.hotel.observer;

import com.hotel.model.Room;

public interface RoomObserver {
    void onRoomStatusChanged(Room room);
}