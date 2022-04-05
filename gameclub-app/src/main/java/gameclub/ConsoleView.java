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

    String groupAdminMenu = "Here are the possible actions:\n1. View list of all games\n2. Add my game\n3. Create Join request\n4. Handle Join request\n5. Quit application\nPlease choose an item: ";
    String playerMenu = "Here are the possible actions:\n1. View list of all games\n2. Add my game\n3. Create Join request\n4. Quit application\nPlease choose an item: ";

    public int PLayerMenu(){
        return printMenu(playerMenu);
    }

    public int GroupAdminMenu(){
        return printMenu(groupAdminMenu);
    }

    private int printMenu(String menuText){
        Scanner sc = new Scanner(System.in);
        System.out.print(menuText);
        int input = sc.nextInt();
        return input;
    }

    // SubMenus

    public void ListAllGames(){
        System.out.println("not ready");
    }

    String gameListHeader = "Please choose a game from the following list add: ";
    String chooseGame = "Please choose game: ";

    public long AddGame(HashMap<Long, String> gameList){

        String answer = PrintListWithHeaderAndQuestion(gameList,gameListHeader,chooseGame);
        return Long.parseLong(answer);
    }

    public void AddGameSuccess(){}


    String joinReqHeader = "Please choose the group you would like to join: ";
    String chooseGroup = "Please choose group: ";

    public long RequestJoin(HashMap<Long, String> groupList){

        String answer = PrintListWithHeaderAndQuestion(groupList,joinReqHeader,chooseGroup);
        return Long.parseLong(answer);
    }

    public void RequestJoinSuccess(){}

    String handleRequestHeader = "List of players who would like to join your group. Please select player number and (A)ccept or (R)eject: ";
    String choosePlayer = "Please answer: ";

    public String HandleRequests(HashMap<Long, String> groupList){
        return PrintListWithHeaderAndQuestion(groupList,handleRequestHeader,choosePlayer);
    }

    public void Close(){
        System.out.println("Bye");
    }

    // Util

    private String PrintListWithHeaderAndQuestion(HashMap<Long, String> list, String header, String question){
        Scanner sc = new Scanner(System.in);
        System.out.println(header);
        list.forEach((g,l) -> System.out.println(g + ". " + l));
        System.out.print(question);
        String input = sc.nextLine();
        return input;
    }

    private String PrintListWithHeaderAndQuestion(ArrayList<Long> IDList, ArrayList<String> Itemlist, String header, String question){
        Scanner sc = new Scanner(System.in);
        System.out.println(header);
        for (int i = 0; i < IDList.size(); i++){
            System.out.println(i + Itemlist.get(i));
        }
        System.out.print(question);
        String stringInput = sc.nextLine().stripTrailing();
        int numericInput = Integer.parseInt(stringInput.substring(0,stringInput.length() - 1));
        String input = IDList.get(numericInput).toString() + stringInput.substring(stringInput.length() - 1,stringInput.length());
        return input;
    }

}
