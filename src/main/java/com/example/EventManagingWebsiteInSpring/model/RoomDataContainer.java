package com.example.EventManagingWebsiteInSpring.model;

/**
 * Wrapper for room data, serve as similar purpose as the toString method
 */
public class RoomDataContainer {
    public final String roomName;
    public final int capacity;
    public final String availableTime;
    public final String schedule;
    public final String features;

    public RoomDataContainer(String roomName, int capacity, String availableTime, String schedule, String features) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.availableTime = availableTime;
        this.schedule = schedule;
        this.features = features;
    }
}
