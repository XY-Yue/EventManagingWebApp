package com.example.EventManagingWebsiteInSpring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoomSearchController {

    @PostMapping("/searchEvent")
    public String searchEvent(Model model){
        return "SearchEvent";
    }
}
