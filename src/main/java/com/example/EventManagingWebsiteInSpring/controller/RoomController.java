package com.example.EventManagingWebsiteInSpring.controller;

import com.example.EventManagingWebsiteInSpring.model.RoomManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
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
        return "SearchRoom";
    }

    @PostMapping("/validateName")
    @ResponseBody
    public Map<String, Boolean> validateRoomName(Model model, @RequestParam("roomName") String roomName){
        RoomManager rm = new RoomManager();
        return Collections.singletonMap("status", validateRoomName(roomName, rm));
    }

    private boolean validateRoomName(String roomName, RoomManager rm){
        return !rm.hasRoom(roomName);
    }

    private void addRoom(RoomInitDTO roomInitDTO, RoomManager rm){

    }
}
