package com.example.EventManagingWebsiteInSpring.model;

/**
 * Wrapper for room data, serve as similar purpose as the toString method
 */
public class RoomDataContainer {
    public final String roomName;
    public final int capacity;
    public final String availableTime;
//    public final String schedule;
    public final String features;

    /**
     * Takes string or primitive data types only
     * @param roomName the name of the room
     * @param capacity the capacity of the room, an integer
     * @param availableTime the available times of the room, parsed into a string
//     * @param schedule schedule of the room, parsed into a string
     * @param features features of the room, parsed into a string
     */
    public RoomDataContainer(String roomName, int capacity, String availableTime, /*String schedule,*/ String features) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.availableTime = availableTime;
//        this.schedule = schedule;
        this.features = features;
    }
}
