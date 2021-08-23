package com.example.EventManagingWebsiteInSpring.controller;

import com.example.EventManagingWebsiteInSpring.model.RoomManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class RoomController {

    @GetMapping("/searchRoom")
    public String searchRoom(Model model){
        return "SearchRoom";
    }

    @PostMapping("/addRoom")
    public String addRoom(Model model){
        return "AddRoom";
    }

    @PostMapping("/addingRoom")
    public String addingRoom(Model model, RoomInitDTO roomInit){
        RoomManager rm = new RoomManager();
        addRoom(roomInit, rm);
        return "SearchRoom";
    }

    @PostMapping("/validateName")
    @ResponseBody
    public Map<String, Boolean> validateRoomName(@RequestParam("roomName") String roomName){
        RoomManager rm = new RoomManager();
        return Collections.singletonMap("status", validateRoomName(roomName, rm));
    }

    private boolean validateRoomName(String roomName, RoomManager rm){
        return !rm.hasRoom(roomName);
    }

    private void addRoom(RoomInitDTO roomInitDTO, RoomManager rm){
        rm.addRoom(roomInitDTO.capacity, parseAvailableTime(roomInitDTO.available),
                roomInitDTO.roomName, new ArrayList<>()/* TODO: features of rooms */);
    }

    private Integer[][] parseAvailableTime(String available){
        available = available.substring(1, available.length() - 1);
        String[] availableHours = available.split(",");

        List<Integer[]> availableTimes = new ArrayList<>();
        int i = 0;
        while (i < availableHours.length){
            int j = i + 1;
            while (j < availableHours.length && Integer.parseInt(availableHours[j]) ==
                    Integer.parseInt(availableHours[j - 1] + 1)){
                j++;
            }
            availableTimes.add(new Integer[]{Integer.parseInt(availableHours[i]),
                    Integer.parseInt(availableHours[j - 1] + 1)});

            i = j;
        }
        return availableTimes.toArray(new Integer[0][]);
    }
}
