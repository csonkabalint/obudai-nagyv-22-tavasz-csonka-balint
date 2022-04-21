package gameclub.service;

import gameclub.domain.*;
import gameclub.persistence.GameRepository;
import gameclub.persistence.GroupRepository;
import gameclub.persistence.JoinRequestRepository;
import gameclub.persistence.PlayerRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@Service
public class GameClubService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private JoinRequestRepository joinRequestRepository;

    @Autowired
    public IdentityManager identityManager;

    @PostConstruct
    public void InitData(){
        gameRepository.save(new Game("Catan Telepesei",
                "A Catan telepesei a legtöbb társasjáték rajongónak az első lépés ami túlmutat a gyermekkori klasszikus dobok és lépek játékokon. A játék célja Catan szigetén megszerezni az uralmat…",
                10,
                new Limits(60, 120),
                new Limits(3, 4),
                Arrays.asList(Category.STRATEGY)));
        gameRepository.save(new Game("Pandemic",
                "'Megvan benned a képesség és a bátorság ahhoz, hogy megmentsd az emberiséget ? Az izgalmas stratégiai játékban egy járványelhárító csapat szakképzett tagjaként feladatod, hogy felfedezd a halálos járvány ellenszérumát, még mielőtt az világszerte elterjedne…",
                8,
                new Limits(45, 60),
                new Limits(2, 4),
                Arrays.asList(Category.STRATEGY)));

        playerRepository.save(new Player("nagys","Nagy Sándor","ns-secret","nagy.sandor@gmail.com", Arrays.asList(Role.SUPERUSER),null));
        playerRepository.save(new Player("horvatha","Horváth Adám","ha-secret","horvath.adam@gmail.com", Arrays.asList(Role.GROUP_ADMIN, Role.PLAYER),Arrays.asList(gameRepository.findById(1L).orElse(null), gameRepository.findById(2L).orElse(null))));
        playerRepository.save(new Player("kovacsp","Kovács Péter","kp-secret","kovacs.peter@gmail.com", Arrays.asList(Role.PLAYER),Arrays.asList(gameRepository.findById(1L).orElse(null))));
        playerRepository.save(new Player("kissi","Kiss István","ki-secret","kiss.istvan@gmail.com", Arrays.asList(Role.PLAYER),Arrays.asList(gameRepository.findById(2L).orElse(null))));
        playerRepository.save(new Player("","a","","a@gmail.com", Arrays.asList(Role.PLAYER),Arrays.asList(gameRepository.findById(2L).orElse(null))));
        playerRepository.save(new Player("janika","Bodrogi János","janika1","janiabodrogon@gmail.com", Arrays.asList(Role.GROUP_ADMIN,Role.PLAYER),Arrays.asList(gameRepository.findById(2L).orElse(null))));


        groupRepository.save(new Group("Óbudai Informatika Játék Csoport",playerRepository.findById(2L).orElse(null),Arrays.asList(playerRepository.findById(3L).orElse(null)),null));
        groupRepository.save(new Group("Vácrátóti Gammás Csoport",playerRepository.findById(6L).orElse(null),Arrays.asList(playerRepository.findById(1L).orElse(null)),null));

        joinRequestRepository.save(new JoinRequest(JoinRequestState.REQUESTED,groupRepository.findById(1L).orElse(null),playerRepository.findById(4L).orElse(null)));
        joinRequestRepository.save(new JoinRequest(JoinRequestState.REQUESTED,groupRepository.findById(1L).orElse(null),playerRepository.findById(6L).orElse(null)));
        joinRequestRepository.save(new JoinRequest(JoinRequestState.REQUESTED,groupRepository.findById(2L).orElse(null),playerRepository.findById(2L).orElse(null)));

        System.out.print(gameRepository.count() + ",");
        System.out.print(playerRepository.count()+ ",");
        System.out.print(groupRepository.count()+ ",");
        System.out.println(joinRequestRepository.count()+ ",");
    }

    public boolean VerifyLogin(String loginName, String loginPassword) throws NoUserFoundException {

        if(playerRepository.findByLoginName(loginName).getPassword().equals(loginPassword)){
            return true;
        }
        else{
            throw new NoUserFoundException("No such User found");
        }
    }


    public void SetCurrentPLayer(String loginName, String loginPassword){
        Player currentPlayer = playerRepository.findByLoginNameAndPassword(loginName, loginPassword);
        identityManager.setCurrentPLayer(currentPlayer);
    }

    public HashMap<Long, String> ListGames(){
        HashMap<Long, String> gameList = new HashMap<>();
        for (Game game : gameRepository.findAll()){
            gameList.put(game.getId(),game.getName());
        }
        return gameList;
    }

   public ArrayList<String> ListGameDescriptions(){
        ArrayList<String> desc = new ArrayList<>();
        for(Game game : gameRepository.findAll()){
            desc.add(game.toString());
        }
        return desc;
    }

     public void AddGame(long gameID){
        if (!identityManager.getCurrentPLayer().getGames().stream().anyMatch(g -> g.getId() == gameID)){
            identityManager.getCurrentPLayer().getGames().add(gameRepository.findById(gameID).orElse(null));
        }
    }

    public HashMap<Long, String> ListGroups(){
        HashMap<Long, String> groupList = new HashMap<>();
        for (Group group : groupRepository.findAll()){
            groupList.put(group.getId(),group.getName());
        }
        return groupList;
    }

   public HashMap<Long, String> ListJoinRequests(){
        HashMap<Long, String> joinRequestsList = new HashMap<>();
        for (JoinRequest joinRequest : joinRequestRepository.findAll()){
            joinRequestsList.put(joinRequest.getPlayer().getId(),joinRequest.getPlayer().getName());
        }
        return joinRequestsList;
    }

    public HashMap<Long, String> ListJoinRequestsForGroupAdmin(long adminID){
        HashMap<Long, String> joinRequestsList = new HashMap<>();
        for (var joinRequest : (joinRequestRepository.findAll().stream().filter(r ->
                groupRepository.findAll().stream().filter(g -> g.getAdmin().getId() == adminID).anyMatch(g ->g.getId() == r.getGroup().getId())).toArray())){
            joinRequestsList.put(((JoinRequest)joinRequest).getPlayer().getId(),((JoinRequest)joinRequest).getPlayer().getName());
        }
        return joinRequestsList;
    }

    public void CreateJoinRequest(long groupID){
        joinRequestRepository.save(new JoinRequest(JoinRequestState.REQUESTED,groupRepository.findById(groupID).orElse(null),identityManager.getCurrentPLayer()));
    }

    public void EvaluateJoinRequest(String evaluation,long userID){
        JoinRequest request = joinRequestRepository.findAll().stream().filter(j -> (j.getPlayer().getId() == userID && j.getGroup().getAdmin().getId() ==
                identityManager.getCurrentPLayer().getId())).findFirst().orElse(null);

        if (evaluation.toUpperCase().equals("A")){
            request.setState(JoinRequestState.ACCEPTED);
            groupRepository.findAll().stream().filter(g -> g.getId() == request.getGroup().getId()).findFirst().orElse(null).getMembers().add(
                   playerRepository.findAll().stream().filter(p -> p.getId() == userID).findFirst().orElse(null));
        }
        else if(evaluation.toUpperCase().equals("R")){
            request.setState(JoinRequestState.REJECTED);
        }
    }

    public void EvaluateJoinRequest(String evaluationWithID){

        long userID = Long.parseLong(evaluationWithID.substring(0,evaluationWithID.length() - 1));
        String evaluation = evaluationWithID.substring(evaluationWithID.length() - 1,evaluationWithID.length()).toUpperCase();

        EvaluateJoinRequest(evaluation,userID);
    }

    public void CloseService(){
        identityManager.currentPLayer = null;
    }

}

