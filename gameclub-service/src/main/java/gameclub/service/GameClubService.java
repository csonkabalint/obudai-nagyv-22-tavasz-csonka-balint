package gameclub.service;

import gameclub.domain.*;
import gameclub.dto.*;
import gameclub.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


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
    private EventRepository eventRepository;

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





    public List<GameDTO> GetGameList(){
        ArrayList<GameDTO> gameDTOList = new ArrayList<>();
        for (Game game : gameRepository.findAll()){
            gameDTOList.add(new GameDTO(game));
        }
        return gameDTOList;
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

    public void AddNewGame(GameDTO gameDTO){
        Game game = new Game();
        game.setName(gameDTO.getName());
        game.setMinimumAge(gameDTO.getMinimumAge());
        game.setDescription(gameDTO.getDescription());
        for(String category : gameDTO.getCategories()){
            game.getCategories().add(Category.valueOf(category));
        }
        Limits noPlayers = new Limits();
        noPlayers.setMin(gameDTO.getNumberOfPlayersMin());
        noPlayers.setMax(gameDTO.getNumberOfPlayersMax());
        game.setNumberOfPlayers(noPlayers);
        Limits playtime = new Limits();
        playtime.setMin(gameDTO.getPlayTimeMin());
        playtime.setMax(gameDTO.getPlayTimeMax());
        game.setPlayTime(playtime);
        System.out.println(game.getCategories().get(0));
        gameRepository.save(game);
    }


    public List<GroupDTO> GetGroupList(){
        ArrayList<GroupDTO> groupDTOList = new ArrayList<>();
        for (Group group : groupRepository.findAll()){
            groupDTOList.add(new GroupDTO(group));
        }
        return groupDTOList;
    }

    public List<GroupDTO> GetUserGroupList(){
        ArrayList<GroupDTO> groupDTOList = new ArrayList<>();
        for (Group group : groupRepository.findByMembers(GetAuthenticatedPlayer()) /*groupRepository.findAll().stream().filter(g -> g.getMembers().contains(GetAuthenticatedPlayer()))*/){
            groupDTOList.add(new GroupDTO(group));
        }
        return groupDTOList;
    }

    public List<GroupDTO> GetOtherGroupList(long groupID){
        ArrayList<GroupDTO> groupDTOList = new ArrayList<>();
        Player player = GetAuthenticatedPlayer();
        int i = 0;
        for (Group group : groupRepository.findAll().stream().filter(g -> !g.getMembers().contains(player)).collect(Collectors.toList())){
            groupDTOList.add(new GroupDTO(group));
            if(joinRequestRepository.findAll().stream().filter(j -> j.getGroup() == group && j.getPlayer() == player).findAny().orElse(null) != null){
                groupDTOList.get(i).setHasRequested(true);
            }
            else{
                groupDTOList.get(i).setHasRequested(false);
            }
            i++;
        }
        return groupDTOList;
    }

    public GroupDTO GetGroup(long groupID){
        GroupDTO group = new GroupDTO(groupRepository.findById(groupID).orElse(null));
        return group;
    }



    public List<JoinRequestDTO> GetJoinRequests(){
        UserDetailContainer userDetails = (UserDetailContainer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Player player = playerRepository.findByLoginName(userDetails.getUsername());
        Group group = groupRepository.findByAdmin(player);
        List<JoinRequest> joinRequests = joinRequestRepository.findAll().stream().filter(j -> j.getGroup().getAdmin() == player).collect(Collectors.toList());
        List<JoinRequestDTO> joinRequestDTOs = new ArrayList<>();
        for (JoinRequest j : joinRequests){
            joinRequestDTOs.add(new JoinRequestDTO(j));
        }
        for (JoinRequestDTO j : joinRequestDTOs){
            System.out.println(j.getPlayerName());
        }
        return joinRequestDTOs;
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
        /*if(groupRepository.findById(groupID).get().getMembers().contains(GetAuthenticatedPlayer()))
        {

        }*/
        joinRequestRepository.save(new JoinRequest(JoinRequestState.REQUESTED,groupRepository.findById(groupID).orElse(null),GetAuthenticatedPlayer()));
        System.out.println(joinRequestRepository.count()+ ",");
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

    public List<EventDTO> GetGroupEvents(){
        UserDetailContainer userDetails = (UserDetailContainer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Player player = playerRepository.findByLoginName(userDetails.getUsername());
        Group group = groupRepository.findByAdmin(player);
        List<EventDTO> eventDTOs = new ArrayList<>();
        for(Event e : group.getEvents()){
            eventDTOs.add(new EventDTO(e));
        }
        return eventDTOs;
    }

    public void AddEvent(String location){
        UserDetailContainer userDetails = (UserDetailContainer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Player player = playerRepository.findByLoginName(userDetails.getUsername());
        Group group = groupRepository.findByAdmin(player);
        Event event = new Event(LocalDateTime.now(),location);
        eventRepository.save(event);
        group.AddEvent(event);
        groupRepository.save(group);
    }

    public void AddEvent(String date, String location, String description) {
        UserDetailContainer userDetails = (UserDetailContainer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Player player = playerRepository.findByLoginName(userDetails.getUsername());
        Group group = groupRepository.findByAdmin(player);
        Event event = new Event();
        //dátumo be kell még állitani
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //event.setDate(); = event.getDate().format(formatter);
        event.setDate(LocalDateTime.now());
        event.setPlace(location);
        event.setDescription(description);
        eventRepository.save(event);
        group.AddEvent(event);
        groupRepository.save(group);
    }


    @ModelAttribute("player")
    public Player GetAuthenticatedPlayer(){
        UserDetailContainer userDetails = (UserDetailContainer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Player player = playerRepository.findByLoginName(userDetails.getUsername());
        return player;
    }


    //ne hasznald
    public PlayerRoleDTO GetPlayerRole(){
        UserDetailContainer userDetails = (UserDetailContainer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Role> roles =playerRepository.findByLoginName(userDetails.getUsername()).getRoles();
        PlayerRoleDTO playerRole = new PlayerRoleDTO(Role.PLAYER);
        return playerRole;
    }

    private List<Role> GetPlayerRoles(){
        UserDetailContainer userDetails = (UserDetailContainer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Role> roles =playerRepository.findByLoginName(userDetails.getUsername()).getRoles();
        return roles;
    }

    public boolean IsAdmin(){
        if(GetPlayerRoles().contains(Role.GROUP_ADMIN)){
            return true;
        }
        return false;
    }

    public boolean IsSuperUser(){
        if(GetPlayerRoles().contains(Role.SUPERUSER)){
            return true;
        }
        return false;
    }

    public GroupDTO GetAdminGroup(){
        UserDetailContainer userDetails = (UserDetailContainer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Player player = playerRepository.findByLoginName(userDetails.getUsername());
        Group group = groupRepository.findByAdmin(player);
        return new GroupDTO(group);
    }

    public void AttendEvent(long eventid) {
        Player player = GetAuthenticatedPlayer();
        Event event = eventRepository.findById(eventid).orElse(null);
        if (!event.getParticipants().contains(player)){
            event.getParticipants().add(player);
            eventRepository.save(event);
        }

    }


    public List<String> GetGameCategories() {
        List<String> categories = new ArrayList<>();
        for(Category category : Arrays.asList(Category.values())){
            categories.add(category.toString());
        }
        return categories;
    }
}

