package gameclub.persistence;

import gameclub.domain.*;
import java.io.File;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import gameclub.dto.JoinRequestDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class DataStore {


    public ArrayList<Player> players;
    public ArrayList<Game> games;
    public ArrayList<Group> groups;
    public ArrayList<JoinRequest> joinRequests;
    public ArrayList<Event> events;

    ModelMapper modelMapper = new ModelMapper();
    ObjectMapper objectMapper = new ObjectMapper();
    TypeMap<JoinRequestDTO, JoinRequest> joinRequestJoinRequestDTOTypeMap = this.modelMapper.createTypeMap(JoinRequestDTO.class, JoinRequest.class);

    private void InitTypeMap(){
        //joinRequestJoinRequestDTOTypeMap.addMapping();
    }

    public void InitializeData(){



        try {
            players = objectMapper.readValue(new File("Data/users.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Player.class));
            games = objectMapper.readValue(new File("Data/games.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Game.class));
            groups = objectMapper.readValue(new File("Data/groups.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Group.class));
            joinRequests = objectMapper.readValue(new File("Data/joinRequests.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, JoinRequest.class));
            //events = objectMapper.readValue(new File("Data/users.json"), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Event.class));

        }
        catch (Exception ex){
            System.out.println("error message: " + ex.getMessage());
        }

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
