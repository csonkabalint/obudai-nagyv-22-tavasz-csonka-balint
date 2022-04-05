package gameclub.persistence;

import gameclub.domain.*;
import gameclub.dto.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class DataStore {

    // Data

    public ArrayList<Player> players;
    public ArrayList<Game> games;
    public ArrayList<Group> groups;
    public ArrayList<JoinRequest> joinRequests;
    public ArrayList<Event> events;

    public ArrayList<GroupDTO> groupDTOs;
    public ArrayList<JoinRequestDTO> joinRequestDTOs;
    public ArrayList<PlayerDTO> playerDTOs;

    // Data-Init

    private ModelMapper modelMapper = new ModelMapper();
    private ObjectMapper objectMapper = new ObjectMapper();

    TypeMap<JoinRequestDTO, JoinRequest> joinRequestJoinRequestDTOTypeMap = this.modelMapper.createTypeMap(JoinRequestDTO.class, JoinRequest.class);
    TypeMap<PlayerDTO, Player> playerPLayerDTOTypeMap = this.modelMapper.createTypeMap(PlayerDTO.class, Player.class);
    TypeMap<GroupDTO, Group> groupGroupDTOTypeMap = this.modelMapper.createTypeMap(GroupDTO.class, Group.class);

    TypeMap<JoinRequest,JoinRequestDTO> joinRequestDTOJoinRequestTypeMap = this.modelMapper.createTypeMap(JoinRequest.class, JoinRequestDTO.class);


    private void InitTypeMap(){

        // JoinRequest converters
        Converter<Integer, Player> playerIdToPlayerRef = pl -> players.stream().filter(p -> p.getId() == pl.getSource()).findFirst().orElse(null);
        Converter<Integer, Group> groupIdToGroupRef = gr -> groups.stream().filter(g -> g.getId() == gr.getSource()).findFirst().orElse(null);
        joinRequestJoinRequestDTOTypeMap.addMappings(mapping -> mapping.using(playerIdToPlayerRef).map(JoinRequestDTO::getUserId,JoinRequest::setPlayer));
        joinRequestJoinRequestDTOTypeMap.addMappings(mapping -> mapping.using(groupIdToGroupRef).map(JoinRequestDTO::getGroupId,JoinRequest::setGroup));

        // JoinRequestDTO converters
        Converter<Player, Integer> playerRefToPlayerId = pl -> (int)(pl.getSource().getId());
        Converter<Group, Integer> groupRefToGroupId = gr -> (int)(gr.getSource().getId());
        joinRequestDTOJoinRequestTypeMap.addMappings(mapping -> mapping.using(playerRefToPlayerId).map(JoinRequest::getPlayer,JoinRequestDTO::setUserId));
        joinRequestDTOJoinRequestTypeMap.addMappings(mapping -> mapping.using(groupRefToGroupId).map(JoinRequest::getGroup,JoinRequestDTO::setGroupId));


        // Player converters
        Converter<ArrayList<Long>,ArrayList<Game>> gameIdToGameRef = ga -> new ArrayList<Game>((games.stream().filter(g -> ga.getSource().contains(g.getId())).collect(Collectors.toList())));
        playerPLayerDTOTypeMap.addMappings(mapping -> mapping.using(gameIdToGameRef).map(PlayerDTO::getGames,Player::setGames));

        // Group converters
        Converter<ArrayList<Long>,ArrayList<Player>> playerIdsToPlayerRefs = pl -> new ArrayList<Player>((players.stream().filter(p -> pl.getSource().contains(p.getId())).collect(Collectors.toList())));
        //Converter<ArrayList<Long>,ArrayList<Event>> eventIdsToEventRefs = ev -> new ArrayList<Event>((events.stream().filter(e -> ev.getSource().contains(e.getId())).collect(Collectors.toList())));
        Converter<Long, Player> adminIdToAdminRef = pl -> players.stream().filter(p -> p.getId() == pl.getSource()).findFirst().orElse(null);
        groupGroupDTOTypeMap.addMappings(mapping -> mapping.using(playerIdsToPlayerRefs).map(GroupDTO::getMembers,Group::setMembers));
        groupGroupDTOTypeMap.addMappings(mapping -> mapping.using(adminIdToAdminRef).map(GroupDTO::getAdmin,Group::setAdmin));

    }


    @PostConstruct
    public void InitializeData(){

        InitTypeMap();

        try {

            //szamít a sorrend, player kollekciot, games kollekcio nelkul nem lehet letrehozni!!!
            games = objectMapper.readValue(new File("Data/games.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Game.class));
            playerDTOs = objectMapper.readValue(new File("Data/users.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, PlayerDTO.class));
            players = MapDTOtoClass(playerPLayerDTOTypeMap,playerDTOs);
            groupDTOs = objectMapper.readValue(new File("Data/groups.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, GroupDTO.class));
            groups = MapDTOtoClass(groupGroupDTOTypeMap,groupDTOs);
            joinRequestDTOs = objectMapper.readValue(new File("Data/joinRequests.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, JoinRequestDTO.class));
            joinRequests = MapDTOtoClass(joinRequestJoinRequestDTOTypeMap,joinRequestDTOs);
            //events = objectMapper.readValue(new File("Data/users.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Event.class));

        }
        catch (Exception ex){
            System.out.println("error message: " + ex.getMessage());
        }

        System.out.println("data done");
    }

    private <Dt,Cl>  ArrayList<Cl> MapDTOtoClass(TypeMap<Dt,Cl> typeMap, ArrayList<Dt> dtoCollection){

        ArrayList<Cl> classCollection = new ArrayList<Cl>();

        for (int i = 0; i < dtoCollection.size(); i++){
            try {
                classCollection.add(typeMap.map(dtoCollection.get(i)));
            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        return classCollection;
    }

    private <Cl, Dl>  ArrayList<Dl> MapClassToDTO(TypeMap<Cl, Dl> typeMap, ArrayList<Cl> classCollection){

        ArrayList<Dl> dtoCollection = new ArrayList<Dl>();

        for (int i = 0; i < classCollection.size(); i++){
            try {
                dtoCollection.add(typeMap.map(classCollection.get(i)));
            }
            catch (Exception ex){

                System.out.println(ex.getMessage());
            }
        }
        return dtoCollection;
    }

    // On CLOSE

    public void SaveChangesToJson(){
        joinRequestDTOs = MapDTOtoClass(joinRequestDTOJoinRequestTypeMap,joinRequests);
        try{
            objectMapper.writeValue(new File("Data/joinRequests.json"),joinRequestDTOs);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        System.out.println("data saved");
    }

    // CRUD

    public Player GetPlayer(String loginName){
        return players.stream().filter(p -> p.getLoginName().equals(loginName)).findFirst().orElse(null);
    }

    public Player GetPlayer(String loginName, String loginPassword){
        return players.stream().filter(p -> p.getLoginName().equals(loginName) && p.getPassword().equals(loginPassword)).findFirst().orElse(null);
    }

    public List<Game> GetGames(){
        return games;
    }

    public Game GetGame(long gameID){
        return games.stream().filter(g -> g.getId() == gameID).findFirst().orElse(null);
    }

    public List<Group> GetGroups(){
        return groups;
    }

    public List<JoinRequest> GetJoinRequests(){
        return joinRequests;
    }

    public void CreateJoinRequest(long groupID, long userID){
        //itt  majd csekkolni kellene, hogy egyaltalan letezik-e a csoport
        joinRequests.add(new JoinRequest(
                JoinRequestState.REQUESTED,
                groups.stream().filter(g -> g.getId() == groupID).findFirst().orElse(null),
                players.stream().filter(p -> p.getId() == userID).findFirst().orElse(null)
        ));
    }

}
