package gameclub;

import gameclub.service.GameClubService;
import gameclub.service.NoUserFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
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
        Login(ctx);
        Menu(ctx);
        Close(ctx);
    }

    public void Close(ConfigurableApplicationContext ctx){
        gameClubService.CloseService();
        int exitCode = SpringApplication.exit(ctx, () -> 0);
        System.exit(exitCode);
    }

    public void Login(ConfigurableApplicationContext ctx){
        String loginName = consoleView.LoginName();
        String loginPassword = consoleView.LoginPassword();
        try {
            if (gameClubService.VerifyLogin(loginName, loginPassword)) {
                gameClubService.SetCurrentPLayer(loginName, loginPassword);
                consoleView.LoginSuccess(gameClubService.identityManager.getCurrentPLayer().getRoles().toString());
            } else {
                consoleView.LoginFailure();
            }
        }
        catch (NoUserFoundException ex){
            consoleView.LoginFailure();
            consoleView.PrintException(ex.getMessage());
            Close(ctx);
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
                consoleView.ListAllGames(gameClubService.ListGameDescriptions());
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
                /*consoleView.Close();
                Close(ctx);*/
            default:
                break;
        }
    }

    private void GroupAdminMenu(ConfigurableApplicationContext ctx){
        int choice = consoleView.GroupAdminMenu();

        switch(choice) {

            case 1:
                consoleView.ListAllGames(gameClubService.ListGameDescriptions());
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
                /*consoleView.Close();
                Close(ctx);*/
            default:
                break;
        }
    }
}
