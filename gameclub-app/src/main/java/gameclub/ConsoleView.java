package gameclub;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@Component
public class ConsoleView {

    // Login

    String loginName = "Login name: ";
    String loginPassword = "Password: ";
    String loginSuccess = "Login successful. Your role: ";
    String loginFailure = "Login failure, bye.";

    public String LoginName(){
        Scanner sc = new Scanner(System.in);
        System.out.print(loginName);
        String input = sc.nextLine();
        return input;
    }

    public String LoginPassword(){
        Scanner sc = new Scanner(System.in);
        System.out.print(loginPassword);
        String input = sc.nextLine();
        return input;
    }

    public void LoginSuccess(String role){
        System.out.println(loginSuccess + role);
    }

    public void LoginFailure(){
        System.out.println(loginFailure);
    }

    // Menu

    String manu = "Here are the possible actions:\n1. View list of all games\n2. Add my game\n3. Create Join request\n4. Handle Join request\n5. Quit application\nPlease choose an item:";

    public int Menu(){
        Scanner sc = new Scanner(System.in);
        System.out.print(manu);
        int input = sc.nextInt();
        return input;
    }

    // SubMenus

    String gameListHeader = "Please choose a game from the following list add: ";
    String chooseGame = "Please choose game: ";

    public long AddGame(HashMap<Long, String> gameList){

        String answer = PrintListWithHeaderAndQuestion(gameList,gameListHeader,chooseGame);
        return Long.parseLong(answer);
        /*Scanner sc = new Scanner(System.in);
        System.out.println(gameListHeader);
        gameList.forEach((g,l) -> System.out.println(g + ". " + l));
        System.out.print(chooseGame);
        long input = sc.nextInt();
        return input;*/
    }

    public void AddGameSuccess(){}


    String joinReqHeader = "Please choose the group you would like to join: ";
    String chooseGroup = "Please choose group: ";

    public long RequestJoin(HashMap<Long, String> groupList){

        String answer = PrintListWithHeaderAndQuestion(groupList,joinReqHeader,chooseGroup);
        return Long.parseLong(answer);
        /*Scanner sc = new Scanner(System.in);
        System.out.println(joinReqHeader);
        groupList.forEach((g,l) -> System.out.println(g + ". " + l));
        System.out.print(chooseGroup);
        long input = sc.nextInt();
        return input;*/
    }

    public void RequestJoinSuccess(){}

    public String HandleRequests(HashMap<Long, String> groupList){
        return "1A";
    }

    // Util

    private String PrintListWithHeaderAndQuestion(HashMap<Long, String> list, String header, String question){
        Scanner sc = new Scanner(System.in);
        System.out.println(header);
        list.forEach((g,l) -> System.out.println(g + ". " + l));
        System.out.print(question);
        String input = sc.nextLine();
        //ArrayList<Integer> keys = list.keySet();
        return input;
    }

}
