package gameclub.web;

import gameclub.domain.Player;
import gameclub.service.GameClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {

    @Autowired
    public GameClubService gameClubService;

    @GetMapping(value = "/home")
    public String Home(Model model, @ModelAttribute("player")Player player){
        model.addAttribute("logedInPlayer", gameClubService.GetAuthenticatedPlayer());
        return "home";
    }


}
