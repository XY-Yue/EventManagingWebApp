package com.example.EventManagingWebsiteInSpring.model;

import java.util.Calendar;
import java.util.NavigableMap;

/**
 * An interface that checks the given schedule is available or not
 * @author Group0694
 * @version 2.0.0
 */
public interface Available {
    /**
     * Check if the given schedule is available during this time or not
     * @param schedule A map of schedule
     * @param startTime Start time of this interval
     * @param endTime End time of this interval
     * @return true iff this interval is available with given schedule
     */
    default boolean isAvailable(NavigableMap<Calendar[], String> schedule, Calendar startTime, Calendar endTime) {
        // Assuming the given time is checked with isValidTimeSlots
        Calendar[] currTime = {startTime, endTime};
        Calendar[] lessThan = schedule.lowerKey(currTime);
        Calendar[] greaterThan;
        if (lessThan == null) {
            greaterThan = schedule.higherKey(currTime);
            if (greaterThan == null) {
                return schedule.size() == 0;
            }
        } else {
            greaterThan = schedule.higherKey(lessThan);
            if (greaterThan == null) {
                return true;
            }
        }
        return !currTime[1].after(greaterThan[0]);
    }
}
