package com.example.EventManagingWebsiteInSpring.model;

import com.example.EventManagingWebsiteInSpring.controller.RoomInitDTO;
import com.example.EventManagingWebsiteInSpring.controller.SearchFieldDTO;
import com.example.EventManagingWebsiteInSpring.model.RoomDataContainer;
import com.example.EventManagingWebsiteInSpring.model.RoomManager;
import com.example.EventManagingWebsiteInSpring.model.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class RoomController {

    @Autowired
    private RoomRepo repo;

    @GetMapping("/searchRoom")
    public String searchRoom(){
        return "SearchRoom";
    }

    @RequestMapping("/addRoom")
    public String addRoom(){
        return "AddRoom";
    }

    @PostMapping("/startSearch")
    public String startSearchRoom(Model model, @RequestBody SearchFieldDTO roomSearch){
        List<RoomDataContainer> resultRoom = roomSearcher(roomSearch, new RoomManager());
        model.addAttribute("searchResult", resultRoom);
        return "RoomListDisplay";
    }

    @RequestMapping(value = "/addingRoom", method = RequestMethod.POST)
    public String addingRoom(RoomInitDTO roomInit){
        RoomManager rm = new RoomManager();
        addRoom(roomInit, rm);
        return "redirect:searchRoom";
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
        rm.addRoom(roomInitDTO.getCapacity(), parseAvailableTime(roomInitDTO.getAvailable()),
                roomInitDTO.getRoomName(), new ArrayList<>(), repo);
    }

    private Integer[][] parseAvailableTime(String available){
        available = available.substring(1, available.length() - 1);
        String[] availableHours = available.split(",");

        List<Integer[]> availableTimes = new ArrayList<>();
        int i = 0;
        while (i < availableHours.length){
            int j = i + 1;
            while (j < availableHours.length && Integer.parseInt(availableHours[j]) ==
                    Integer.parseInt(availableHours[j - 1]) + 1){
                j++;
            }
            availableTimes.add(new Integer[]{Integer.parseInt(availableHours[i]),
                    Integer.parseInt(availableHours[j - 1]) + 1});

            i = j;
        }
        return availableTimes.toArray(new Integer[0][]);
    }

    private List<RoomDataContainer> roomSearcher(SearchFieldDTO roomSearch, RoomManager rm){
        int[] available = new int[2];
        if (roomSearch.getAvailableEnd().equals("-") || roomSearch.getAvailableStart().equals("-")){
            available = null;
        }else {
            available[0] = Integer.parseInt(roomSearch.getAvailableStart());
            available[1] = Integer.parseInt(roomSearch.getAvailableEnd());
        }
        return rm.searchRoomWithKeyword(
                roomSearch.getNameKey(),
                roomSearch.getCapacity(),
                available
        );
    }
}
