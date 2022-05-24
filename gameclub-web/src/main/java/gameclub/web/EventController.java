package gameclub.web;

import gameclub.dto.EventDTO;
import gameclub.service.GameClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EventController {

    @Autowired
    private GameClubService gameClubService;

    @PostMapping(value = "/attend")
    public String CreateJoinRequest(@RequestParam String eventid){
        gameClubService.AttendEvent(Long.parseLong(eventid));
        return "redirect:groups";
    }

    @GetMapping(value = "/events")
    public String Events(Model model){
        model.addAttribute("events", gameClubService.GetGroupEvents());
        model.addAttribute("event", new EventDTO());
        return "eventsList";
    }

    @RequestMapping(value = "/addevent", method = RequestMethod.POST)
    public String AddEvent(@ModelAttribute EventDTO event, Model model){
        if(event.getLocation().length() > 0 && event.getDescription().length() > 0) {
            gameClubService.AddEvent(event.getDate(), event.getLocation(), event.getDescription());
        }
        model.addAttribute("events", gameClubService.GetGroupEvents());
        model.addAttribute("event", new EventDTO());
        return "eventsList";
    }

}
