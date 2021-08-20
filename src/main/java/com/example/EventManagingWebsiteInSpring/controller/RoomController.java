package com.example.EventManagingWebsiteInSpring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String validateRoomName(Model model, @RequestParam("roomName") String roomName){
        return "";
    }

    private boolean validateRoomName(String roomName){
        return false;
    }

    private void addRoom(RoomInitDTO roomInitDTO){

    }
}
