package gameclub.web;

import gameclub.service.GameClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JoinRequestController {

    @Autowired
    GameClubService gameClubService;

    @PostMapping(value = "/createjoinrequest")
    public String CreateJoinRequest(@RequestParam String groupid){
        gameClubService.CreateJoinRequest(Long.parseLong(groupid));
        return "redirect:groups";
    }

    @PostMapping(value = "/acceptreq")
    public ModelAndView AcceptRequest(@RequestParam String groupid, @RequestParam String userid){
        gameClubService.AcceptJoinRequest(Long.parseLong(groupid),Long.parseLong(userid));
        return new ModelAndView("redirect:/mygroup");
    }

    @PostMapping(value = "/rejectreq")
    public ModelAndView RejectRequest(@RequestParam String groupid, @RequestParam String userid){
        gameClubService.RejectJoinRequest(Long.parseLong(groupid),Long.parseLong(userid));
        return new ModelAndView("redirect:/mygroup");
    }

}
