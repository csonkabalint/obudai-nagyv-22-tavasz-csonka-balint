package gameclub.web;

import gameclub.domain.Player;
import gameclub.persistence.GameRepository;
import gameclub.service.GameClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @Autowired
    public GameClubService gameClubService;

    @GetMapping(value = "/")
    public String test(Model model){
        addTestAttribute(model);
        return "test";
    }

    public void addTestAttribute(Model model){
        model.addAttribute("name", "balint");
    }

    public void addTestAttributeHome(Model model, String name ){
        model.addAttribute("name", name);
    }

    @GetMapping(value = "/home")
    public String Home(Model model, @ModelAttribute("player")Player player){
        player = gameClubService.GetTestPlayer();
        addTestAttributeHome(model, player.getName());
        return "home";
    }

}
