package gameclub.web;

import gameclub.dto.GameDTO;
import gameclub.service.GameClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping(value = "/games")
    public String Games(Model model){
        model.addAttribute("games", gameClubService.GetUserGameList());
        model.addAttribute("othergames", gameClubService.GetOtherGameList());
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
    public String ListGamesAdd(@ModelAttribute GameDTO game, Model model){
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
