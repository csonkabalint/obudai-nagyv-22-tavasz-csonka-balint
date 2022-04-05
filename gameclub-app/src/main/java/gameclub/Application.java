package gameclub;

import gameclub.service.GameClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;


@ComponentScan(basePackages =  {"gameclub", "gameclub.service", "gameclub.persistence"})
public class Application {


    @Autowired
    private GameClubService gameClubService;

    @Autowired
    private ConsoleView consoleView;

    @Autowired
    @Lazy
    private Application application;


    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ConfigurableApplicationContext ctx) {
        return args -> {
            application.Play(ctx);
        };
    }

    public void Play(ConfigurableApplicationContext ctx){
        Login();
        Menu(ctx);
    }

    public void Close(ConfigurableApplicationContext ctx){
        //meg kell hívni az adat kiiratast, es user eltavolitast!!!
        ctx.close();
    }

    public void Login(){
        String loginName = consoleView.LoginName();
        String loginPassword = consoleView.LoginPassword();

        if (gameClubService.VerifyLogin(loginName, loginPassword)){
            gameClubService.SetCurrentPLayer(loginName, loginPassword);
            consoleView.LoginSuccess(gameClubService.identityManager.getCurrentPLayer().getRoles().toString());
        }
        else{
            consoleView.LoginFailure();
        }
    }

    public void Menu(ConfigurableApplicationContext ctx){
        if(gameClubService.identityManager.AuthorizeAs("GROUP_ADMIN")){
            GroupAdminMenu(ctx);
        }
        else if(gameClubService.identityManager.AuthorizeAs("PLAYER")){
            PlayerMenu(ctx);
        }
    }

    private void PlayerMenu(ConfigurableApplicationContext ctx){
        int choice = consoleView.PLayerMenu();

        switch(choice) {

            case 1:
                consoleView.ListAllGames();
                break;
            case 2:
                long gameID = consoleView.AddGame(gameClubService.ListGames());
                gameClubService.AddGame(gameID);
                break;
            case 3:
                long groupID = consoleView.RequestJoin(gameClubService.ListGroups());
                gameClubService.CreateJoinRequest(groupID);
                break;
            case 4:
                consoleView.Close();
                Close(ctx);
            default:
                break;
        }
    }

    private void GroupAdminMenu(ConfigurableApplicationContext ctx){
        int choice = consoleView.GroupAdminMenu();

        switch(choice) {

            case 1:
                consoleView.ListAllGames();
                break;
            case 2:
                long gameID = consoleView.AddGame(gameClubService.ListGames());
                gameClubService.AddGame(gameID);
                break;
            case 3:
                long groupID = consoleView.RequestJoin(gameClubService.ListGroups());
                gameClubService.CreateJoinRequest(groupID);
                break;
            case 4:
                String reqAnswer = consoleView.HandleRequests(gameClubService.ListJoinRequestsForGroupAdmin(gameClubService.identityManager.GetID()));
                gameClubService.EvaluateJoinRequest(reqAnswer);
                break;
            case 5:
                consoleView.Close();
                Close(ctx);
            default:
                break;
        }
    }
}
