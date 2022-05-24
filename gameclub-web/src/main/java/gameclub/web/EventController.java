package gameclub.web;

import gameclub.service.GameClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {

    @Autowired
    private GameClubService gameClubService;

    @PostMapping(value = "/attend")
    public String CreateJoinRequest(@RequestParam String eventid){
        gameClubService.AttendEvent(Long.parseLong(eventid));
        return "redirect:groups";
    }

}
