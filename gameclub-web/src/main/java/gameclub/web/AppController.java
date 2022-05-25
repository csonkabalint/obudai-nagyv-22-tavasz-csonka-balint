package gameclub.web;

import gameclub.domain.Player;
import gameclub.dto.GameDTO;
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
        if(gameClubService.IsSuperUser()){
            return "redirect:listgamesform";
        }
        model.addAttribute("logedInPlayer", gameClubService.GetAuthenticatedPlayer());
        AddRole(model);
        return "home";
    }

    @GetMapping(value = "/games")
    public String Games(Model model){
        model.addAttribute("games", gameClubService.GetGameList());
        AddRole(model);
        return "gamesList";
    }

    public void AddRole(Model model){
        if(gameClubService.IsAdmin()){
            model.addAttribute("playerrole", "admin");
        }
    }

    @GetMapping(value = "/listgamesform")
    public String ListGamesForm(Model model){
        GameListModel(model);
        return "listGamesForm";
    }

    @RequestMapping(value = "/listgamesadd", method = RequestMethod.POST)
    public String ListGamesAdd(@ModelAttribute GameDTO game,Model model){
        if(ValidateGame(game)){
            gameClubService.AddNewGame(game);
        }
        GameListModel(model);
        return "listGamesForm";
    }

    private boolean ValidateGame(GameDTO game){
        return(game.getName().length() > 0 && game.getDescription().length() > 0 &&
                game.getMinimumAge() > 3 && game.getPlayTimeMax() > game.getPlayTimeMin() &&
                game.getPlayTimeMin() > 0 && game.getNumberOfPlayersMax() > game.getNumberOfPlayersMin() &&
                game.getNumberOfPlayersMin() > 0);
    }

    private void GameListModel(Model model){
        model.addAttribute("games", gameClubService.GetGameList());
        model.addAttribute("game", new GameDTO());
        model.addAttribute("categoryList", gameClubService.GetGameCategories());
    }
}
