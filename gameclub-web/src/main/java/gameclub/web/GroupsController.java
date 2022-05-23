package gameclub.web;

import gameclub.service.GameClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

}
