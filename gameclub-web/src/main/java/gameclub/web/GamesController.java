package gameclub.web;

import gameclub.service.GameClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GamesController {

    @Autowired
    GameClubService gameClubService;

    @PostMapping(value = "/addgame")
    public ModelAndView AddGame(@RequestParam String gameid){
        gameClubService.AddGame(Long.parseLong(gameid));
        return new ModelAndView("redirect:/games");
    }

}
