package gameclub.web;

import gameclub.dto.EventDTO;
import gameclub.service.GameClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GroupsController {

    @Autowired
    private GameClubService gameClubService;

    @GetMapping(value = "/groups")
    public String Groups(Model model){
        model.addAttribute("usergroups", gameClubService.GetUserGroupList());
        model.addAttribute("groups",gameClubService.GetGroupList());
        return "groupsList";
    }

    @GetMapping(value = "/group")
    public String Group(@RequestParam String groupid, Model model){
        model.addAttribute("group",gameClubService.GetGroup(Long.parseLong(groupid)) );
        return "groupDetails";
    }

    @PostMapping(value = "/createjoinrequest")
    public String CreateJoinRequest(@RequestParam String groupid){
        gameClubService.CreateJoinRequest(Long.parseLong(groupid));
        return "redirect:groups";
    }

    @GetMapping(value = "/mygroup")
    public String MyGroup(Model model){
        model.addAttribute("group", gameClubService.GetAdminGroup());
        model.addAttribute("joinrequests", gameClubService.GetJoinRequests());
        return "adminGroup";
    }


    @GetMapping(value = "/events")
    public String Events(Model model){
        model.addAttribute("events", gameClubService.GetGroupEvents());
        model.addAttribute("event", new EventDTO());
        return "eventsList";
    }

    @RequestMapping(value = "/addevent", method = RequestMethod.POST)
    public String AddEvent(@ModelAttribute EventDTO event, Model model){
        gameClubService.AddEvent(event.getLocation());
        model.addAttribute("events", gameClubService.GetGroupEvents());
        model.addAttribute("event", new EventDTO());
        return "eventsList";
    }
}
