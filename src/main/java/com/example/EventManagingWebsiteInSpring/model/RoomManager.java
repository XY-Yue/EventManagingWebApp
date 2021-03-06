package com.example.EventManagingWebsiteInSpring.model;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;

/**
 * An use case class of room.
 * Stores all room in a map which has room name map to the corresponding Room object.
 * Contains constructor of room, which is able to construct a new room.
 * Methods in this class contains check room status, operating on specific room, and get room with given information.
 * All the parameter should be given by controller of room.
 */
public class RoomManager {
    private final Map<String, Room> roomList;
    private List<String> roomFeatureList;

    /**
     * Constructs the RoomManager object
     */
    public RoomManager(){
        roomList = new HashMap<>();
        roomFeatureList =
                new ArrayList<>(Arrays.asList("Projector", "Row of chairs", "Table", "Computers"));
        readData();
    }

    // Writes all data to a file
    private void saveData(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("room_data.txt"));
            Gson g = new Gson();
            writer.write(g.toJson(roomFeatureList));
            writer.newLine();
            for (Room room : roomList.values()){
                writer.write(g.toJson(room));
                writer.newLine();
            }
            writer.close();
        }catch (IOException iox){
            System.out.println("File writing failed.");
        }
    }

    // Read all room object from a file
    private void readData(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("room_data.txt"));
            Gson g = new Gson();
            this.roomFeatureList = Arrays.asList(g.fromJson(reader.readLine(), String[].class));
            String line = reader.readLine();
            while (line != null && line.length() != 0){
                Room room = g.fromJson(line, Room.class);
                this.roomList.put(room.getRoomName(), room);
                line = reader.readLine();
            }
        }catch (IOException iox){
            System.out.println("File not found.");
        }
    }

    /**
     * Gives the toString description of room with given name.
     * @param roomName the name of room ask for toString description
     * @return the toString description of room
     */
    String printRoom(String roomName) {
        return (roomList.get(roomName) == null) ? null : roomList.get(roomName).toString();
    }

    /**
     * Finds a list of room that satisfies all the given conditions
     * @param timeDuration A sorted collection of time interval where start time is at index 0 and end time is index 1
     * @param features Required a list of String representation of additional features
     * @param capacity Required capacity
     * @return A list of room name that satisfies all of the given conditions
     */
    public List<String> availableRooms(SortedSet<Calendar[]> timeDuration,
                                       List<String> features, int capacity){
        List<String> result = new ArrayList<>();
        for (String room : roomList.keySet()){
            if (roomList.get(room) != null && roomList.get(room).hasFeatures(features)) {
                if (this.checkRoomAvailability(timeDuration, room) && checkRoomTimeSlots(timeDuration, room))
                    if (roomList.get(room).getCapacity() >= capacity) {
                        result.add(room);
                    }
            }
        }
        return result;
    }

    /**
     * Adds a room
     * @param capacity Maximum capacity of the given room
     * @param availableTime Available time slots of the room
     * @param roomName A String representation of the room name
     * @param features A list of String representation of additional features
     * @return true iff the room is added successfully
     */
    public boolean addRoom(int capacity, Integer[][] availableTime, String roomName, List<String> features) {
        // Return true if and only roomName is unique
        // Assume availableTime do not overlap, should check when user input
        if (roomList.get(roomName) == null) {
            Room room = new Room(capacity, availableTime, roomName, features);
            roomList.put(roomName, room);
            saveData();
            return true;
        }
        return false;
    }

    /**
     * Checks the given room name exists or not
     * @param roomName A String representation of the room name
     * @return true iff the room exists
     */
    public boolean hasRoom(String roomName) {
        // Return true iff roomName is in the list
        // For controller to use this method check and then generates different information to the presenter
        // Anytime when asking user to enter room name, this method should be called
        // All other method that needs roomName should assume valid roomName
        return (roomList.get(roomName) != null);
    }

    /**
     * Checks the room is available during the given time interval
     * @param timeDuration A sorted collection of time interval where start time is at index 0 and end time is index 1
     * @param roomName A String representation of the room name
     * @return true iff the room is available during this interval
     */
    public boolean checkRoomAvailability(SortedSet<Calendar[]> timeDuration, String roomName){
        // Assume roomName is in the list
        Room room = roomList.get(roomName);
        if (room == null) {
            return false;
        }
//        for (Calendar[] s : timeDuration) {
//            if (!room.isAvailable(s[0], s[1])) {
//                return false;
//            }
//        }
        return true;
    }

    /**
     * Checks if this room is available with given time slot
     * @param startTime Start time of the given time slot
     * @param endTime End time of the given time slot
     * @param roomName A String representation of the room name
     * @return True iff room is available during this time slot
     */
    public boolean checkRoomAvailability(Calendar startTime, Calendar endTime, String roomName) {
        Room room = roomList.get(roomName);
        return room != null /*&& room.isAvailable(startTime, endTime)*/;
    }

    private boolean checkRoomTimeSlots(SortedSet<Calendar[]> timeDuration, String roomName) {
        Room room = roomList.get(roomName);
        if (room == null) return false;
        for (Calendar[] s : timeDuration) {
            Calendar startTime = s[0];
            Calendar endTime = s[1];

            if (endTime.getTimeInMillis() - startTime.getTimeInMillis() >= 86400000) { // a day has 86400000 ms
                return room.isValidTimeSlots(0, 24);
            }
            int minutes1 = startTime.get(Calendar.MINUTE);
            int hour1 = (minutes1 == 0) ? startTime.get(Calendar.HOUR) : startTime.get(Calendar.HOUR) - 1;
            int minutes2 = endTime.get(Calendar.MINUTE);
            int hour2 = (minutes2 == 0) ? endTime.get(Calendar.HOUR) : endTime.get(Calendar.HOUR) + 1;
            if (!room.isValidTimeSlots(hour1, hour2)) {
                return false;
            }
        }
        return true;
    }

//    /**
//     * Adds an event to a room
//     * @param roomName A String representation of the room name
//     * @param eventID A String representation of the event id
//     * @param timeDuration A sorted collection of time interval where start time is at index 0 and end time is index 1
//     * @return true iff the event is added successfully
//     */
//    public boolean addEventToRoom(String roomName, String eventID, SortedSet<Calendar[]> timeDuration) {
//        // Return boolean not String so controller knows what needs to send to presenter
//        return roomList.get(roomName) != null && roomList.get(roomName).addEventToSchedule(timeDuration, eventID);
//    }
//
//    /**
//     * Removes an event from a room
//     * @param roomName A String representation of the room name
//     * @param eventName A String representation of the event id
//     * @param timeDuration A sorted collection of time interval where start time is at index 0 and end time is index 1
//     * @return true iff the event is removed successfully
//     */
//    public boolean removeEventFromRoom(String roomName, String eventName, SortedSet<Calendar[]> timeDuration) {
//        return roomList.get(roomName) != null && roomList.get(roomName).
//                removeEventFromSchedule(timeDuration, eventName);
//    }

    /**
     * Gets the room capacity
     * @param roomName A String representation of given room
     * @return Maximum capacity of the given room, -1 if the room does not exist
     */
    public int getRoomCapacity(String roomName) {
        if (roomList.get(roomName) == null) return -1;
        return roomList.get(roomName).getCapacity();
    }

    /**
     * Generates a String representation of the room info
     * @return room info for all rooms
     */
    List<String> getRoomsInfo() {
        List<String> roomInfo = new ArrayList<>();
        for(Room r : roomList.values()) {
            roomInfo.add(r.toString());
        }
        return roomInfo;
    }

    /**
     * Gets a list of String representation of the additional features
     * @return An iterator of additional features
     */
    public Iterator<String> getAllFeatures() {
        return roomFeatureList.iterator();
    }

    /**
     * Checks if the feature is in the feature list
     * @param feature A String representation of the additional feature
     * @return true iff the additional feature is in the list
     */
    boolean hasFeature(String feature){
        return roomFeatureList.contains(feature);
    }

    /**
     * Adds a new feature to the feature list
     * @param feature A String representation of the additional feature
     * @return true iff added successfully
     */
    boolean addFeature(String feature){
        return roomFeatureList.add(feature);
    }

    /**
     * Searches through all rooms for the given keywords
     * @param roomNameKey key that room name should contain
     * @param capacity minimum capacity for the desired rooms
     * @param available minimum interval the room should be available
     * @return a list of room data containers that contains all information of the desired rooms.
     */
    public List<RoomDataContainer> searchRoomWithKeyword(String roomNameKey, int capacity,
                                                         int[] available/*, List<String> features*/){
        List<RoomDataContainer> output = new ArrayList<>();

        for (String name : roomList.keySet()){
            if (name.contains(roomNameKey)){
                Room room = roomList.get(name);

                if (available != null && !room.isValidTimeSlots(available[0], available[1])){
                    continue;
                }
                if (room.getCapacity() < capacity){
                    continue;
                }
//                if (!room.hasFeatures(features)){
//                    continue;
//                }
                output.add(room.toStringObject());
            }
        }

        return output;
    }
}

