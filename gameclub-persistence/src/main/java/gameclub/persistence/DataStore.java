package gameclub.persistence;

import gameclub.domain.*;
import java.io.File;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import gameclub.dto.JoinRequestDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class DataStore {


    public ArrayList<Player> players;
    public ArrayList<Game> games;
    public ArrayList<Group> groups;
    public ArrayList<JoinRequest> joinRequests;
    public ArrayList<JoinRequestDTO> joinRequestDTOs;
    public ArrayList<Event> events;

    ModelMapper modelMapper = new ModelMapper();
    ObjectMapper objectMapper = new ObjectMapper();
    TypeMap<JoinRequestDTO, JoinRequest> joinRequestJoinRequestDTOTypeMap = this.modelMapper.createTypeMap(JoinRequestDTO.class, JoinRequest.class);

    private void InitTypeMap(){
        Converter<Integer, Player> playerIdToPlayerRef = pl -> players.stream().filter(p -> p.getId() == pl.getSource()).findFirst().orElse(null);
        Converter<Integer, Group> groupIdToGroupRef = gr -> groups.stream().filter(g -> g.getId() == gr.getSource()).findFirst().orElse(null);
        joinRequestJoinRequestDTOTypeMap.addMappings(mapping -> mapping.using(playerIdToPlayerRef).map(JoinRequestDTO::getUserId,JoinRequest::setPlayer));
        joinRequestJoinRequestDTOTypeMap.addMappings(mapping -> mapping.using(groupIdToGroupRef).map(JoinRequestDTO::getGroupId,JoinRequest::setGroup));


    }

    private void TEST_Mapping(){

        joinRequests = new ArrayList<JoinRequest>();

        for (int i = 0; i < joinRequestDTOs.size(); i++){
            try {
                joinRequests.add(joinRequestJoinRequestDTOTypeMap.map(joinRequestDTOs.get(i)));
            }
            catch (Exception ex ){
                System.out.println(ex.getMessage());
            }

            System.out.println(joinRequests.get(i));
        }
    }

    public void InitializeData(){

        InitTypeMap();

        try {
            players = objectMapper.readValue(new File("Data/users.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Player.class));
            games = objectMapper.readValue(new File("Data/games.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Game.class));
            groups = objectMapper.readValue(new File("Data/groups.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Group.class));
            //joinRequests = objectMapper.readValue(new File("Data/joinRequests.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, JoinRequest.class));
            joinRequestDTOs = objectMapper.readValue(new File("Data/joinRequests.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, JoinRequestDTO.class));
            //events = objectMapper.readValue(new File("Data/users.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Event.class));

        }
        catch (Exception ex){
            System.out.println("error message: " + ex.getMessage());
        }

        TEST_Mapping();
    }


    public void InitializePlayers(){

        try {

            players = objectMapper.readValue(new File("Data/users.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Player.class));
        }
        catch (Exception ex){
            System.out.println("ErrorMsg: " + ex.getMessage());
        }

        for (User var : players)
        {
            System.out.println(var.getId() + " " + var.getName());
        }

    }

}
