package com.example.EventManagingWebsiteInSpring.controller;

public class RoomInitDTO {
    String roomName;
    int capacity;
    String available;

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getAvailable() {
        return available;
    }

    public RoomInitDTO(String roomName, int capacity, String available) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.available = available;
    }
}
