package gameclub;

import gameclub.service.GameClubService;
import gameclub.service.NoUserFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;


@ComponentScan(basePackages = "gameclub")
@SpringBootApplication
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
            application.Play();
        };
    }

    public void Play(){
        System.out.println("running");
        try {
        System.out.println(gameClubService.VerifyLogin("nagys","ns-secret"));}
        catch (Exception ex){
            System.out.println("failed");
        }
        Login();
        Menu();
        Close();
    }

    public void Close(){
        gameClubService.CloseService();
        System.exit(0);
    }

    public void Login(){
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
            Close();
        }
    }

    public void Menu(){
        if(gameClubService.identityManager.AuthorizeAs("GROUP_ADMIN")){
            while (true){
                GroupAdminMenu();
            }
        }
        else if(gameClubService.identityManager.AuthorizeAs("PLAYER")){
            while (true){
                PlayerMenu();
            }
        }
    }

    private void PlayerMenu(){
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
                consoleView.Close();
                Close();
            default:
                break;
        }
    }

    private void GroupAdminMenu(){
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
                consoleView.Close();
                Close();
            default:
                break;
        }
    }
}
