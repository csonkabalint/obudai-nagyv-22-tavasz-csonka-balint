package gameclub;

import gameclub.service.GameClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
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
        System.out.println("Bye");
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            application.Play();
        };
    }

    public void Play(){
        Login();
        Menu();
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

    public void Menu(){
        int choice = consoleView.Menu();

        switch(choice) {
            case 1:

                break;
            case 2:
                long gameID = consoleView.AddGame(gameClubService.ListGames());
                gameClubService.AddGame(gameID);
                break;
            case 3:
                long groupID = consoleView.RequestJoin(gameClubService.ListGroups());
                gameClubService.CreateJoinRequest(groupID);
                break;
            default:
                Menu();
        }
    }

}
