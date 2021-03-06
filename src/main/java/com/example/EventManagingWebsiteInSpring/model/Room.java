package com.example.EventManagingWebsiteInSpring.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.*;

/**
 * An entity class of Room.
 * Stores capacity, available time, unique room name, schedule of a room, and a collection of features
 * Schedule is a map with start time map to event name.
 * availableTime is a map that maps the start hour to end hour of the room open time, 0 <= start time <= 23 and
 * 1 <= end time <= 24, and no overlap.
 */
public class Room /*implements Available*/{
    private final int capacity;
    private final NavigableMap<Integer, Integer> availableTime;
    private final String roomName; //Checked for uniqueness
//    private final NavigableMap<Calendar[], String> schedule;

    private final List<String> features;

    /**
     * Constructs a Room object
     * @param capacity Maximum capacity of this room
     * @param availableTime Time slots that this room is available
     * @param roomName An unique String representation of the room name
     * @param features A List of additional features that this room can provide
     */
    protected Room(int capacity, Integer[][] availableTime, String roomName, List<String> features) {
        this.capacity = capacity;
        // Assume availableTimeSlots do not overlap
        // A list of [[start time 1, end time 1], [start time 2, end time 2]]
        this.availableTime = new TreeMap<>();

        for (Integer[] lst: availableTime) {
            this.availableTime.put(lst[0], lst[1]);
        }

        this.features = new ArrayList<>(features);
        this.roomName = roomName;
//        schedule = new TreeMap<> ((Comparator<Calendar[]> & Serializable)
//                (Calendar[] t1, Calendar[]t2) -> {
//                    // t1 == [startTime1, endTime1]
//                    // t2 == [startTime2, endTime2]
//                    // They won't overlap
//                    if (!t1[1].after(t2[0])) {
//                        return -1;
//                    }
//                    if (!t2[1].after(t1[0])) {
//                        return 1;
//                    }
//                    return 0;
//                }
//        );
    }

    /**
     * Gets the capacity of room.
     * @return capacity of room
     */
    protected int getCapacity() {
        return capacity;
    }

//    /**
//     * Checks if the room is available during the given interval
//     * @param startTime Start time of the interval
//     * @param endTime End time of the interval
//     * @return true iff the room is available during this interval
//     */
//    protected boolean isAvailable(Calendar startTime, Calendar endTime) {
//        return this.isAvailable(this.schedule, startTime, endTime);
//    }

    /**
     * Checks the room is open during the given interval hours
     * @param startHour1 Start Hour of the given interval
     * @param endHour2 End Hour of the given interval
     * @return true iff this room is open during this interval
     */
    protected boolean isValidTimeSlots(int startHour1, int endHour2) {
        // Case if startHour 1 == endHour2
        if (startHour1 == endHour2) {
            if (availableTime.get(startHour1) != null) {
                return true;
            }
            Map.Entry<Integer, Integer> timeslot1 = availableTime.lowerEntry(startHour1);
            return timeslot1.getValue() >= startHour1;
        } else if (startHour1 < endHour2) {
            // Case if start hour < end hour
            Integer endTime = availableTime.get(startHour1);
            if (endTime != null && endTime >= endHour2) {
                return true;
            } else {
                Map.Entry<Integer, Integer> timeslot1 = availableTime.lowerEntry(startHour1);
                Map.Entry<Integer, Integer> timeslot2 = availableTime.lowerEntry(endHour2);
                if (timeslot1 != null && timeslot2 != null) {
                    if (timeslot1.equals(timeslot2)) {
                        return true;
                    }
                    return timeslot1.getValue().equals(timeslot2.getKey());
                }
            }
        } else {
            // Case iff end hour < start hour
            return isValidTimeSlots(0, startHour1) && isValidTimeSlots(endHour2, 24);
        }
        return false;
    }

//    /**
//     * Adds the event to the schedule
//     * @param timeDuration A sorted collection of time interval where start time is at index 0 and end time is index 1
//     * @param eventID A String representation of the event id
//     * @return true iff event is added successfully
//     */
//    protected boolean addEventToSchedule(SortedSet<Calendar[]> timeDuration, String eventID) {
//        // return schedule.putIfAbsent(new Timestamp[]{startTime, endTime}, eventID) == null
//        for (Calendar[] t : timeDuration) {
//            this.schedule.put(t, eventID);
//        }
//        return true; // Since we already check for available room
//    }
//
//    /**
//     * Removes the event from the schedule
//     * @param timeDuration A sorted collection of time interval where start time is at index 0 and end time is index 1
//     * @param eventName A String representation of the event id
//     * @return true iff the event is removed successfully
//     */
//    protected boolean removeEventFromSchedule(SortedSet<Calendar[]> timeDuration, String eventName) {
//        for (Calendar[] t : timeDuration) {
//            schedule.remove(t, eventName);
//        }
//        return true; // Since we already check it
//    }
//
//    // This is a helper method for toString
//    private String printSchedule(){
//        StringBuilder sb = new StringBuilder();
//        for(Calendar[] time: schedule.keySet()){
//            sb.append("\n\t");
//            sb.append("Event ");
//            sb.append(schedule.get(time));
//            sb.append(" is hold in this room from ");
//            sb.append(this.getTime(time[0]));
//            sb.append(" to ");
//            sb.append(this.getTime(time[1]));
//            sb.append(".");
//        }
//        return sb.toString();
//    }
//
//    private String getTime(Calendar time) {
//        Date date = new Date(time.getTimeInMillis());
//        return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(date);
//    }

    /**
     * Generates a String representation of the room available time slots in ascending order.
     * @return a String representation of the room available time slots.
     */
    protected String printAvailableTime() {
        List<String> lst = new ArrayList<>();
        for(Integer startTime: availableTime.keySet()) {
            lst.add(startTime.toString() + "-" + availableTime.get(startTime).toString());
        }
        return String.join(", ", lst);
    }

    /**
     * Represents the string contains all information of room.
     * @return toString description of room
     */
    @Override
    public String toString(){
        StringBuilder featuresString = new StringBuilder();

        for (String feature : features) featuresString.append(feature).append("; ");
        if (featuresString.length() != 0) featuresString.replace(
                featuresString.length() - 2, featuresString.length(), ".");

        return "The name of this room is: " + roomName + "\n" +
                "The capacity of this room is: " + capacity + "\n" +
                "The room is available during: " + printAvailableTime() + "\n" +
//                "The schedule of this room is: " + this.printSchedule() + "\n" +
                "This room has:" + ((features.size() == 0) ? "No features" : featuresString.toString());
    }

    /**
     * Formats the date of this room to strings, and assign them to a container object
     * @return RoomDataContainer that contains all data of this room in strings
     */
    protected RoomDataContainer toStringObject(){
        StringBuilder featuresString = new StringBuilder();
        for (String feature : features) featuresString.append(feature).append("; ");

        return new RoomDataContainer(
                this.roomName,
                this.capacity,
                printAvailableTime(),
//                this.printSchedule(),
                featuresString.toString()
        );
    }

    /**
     * Checks if the room has the list of additional features
     * @param checkedFeatures A list of String representation of the additional features
     * @return true iff the room has all the features given by
     */
    protected boolean hasFeatures(List<String> checkedFeatures){
        return features.containsAll(checkedFeatures);
    }

    public String getRoomName() {
        return roomName;
    }
}
