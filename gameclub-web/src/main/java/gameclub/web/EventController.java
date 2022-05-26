package gameclub.web;

import gameclub.dto.EventDTO;
import gameclub.service.GameClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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
    public ModelAndView AddEvent(@Valid @ModelAttribute EventDTO event, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ModelAndView("redirect:/events");
        }
        gameClubService.AddEvent(event);
        return new ModelAndView("redirect:/events");
    }

}
