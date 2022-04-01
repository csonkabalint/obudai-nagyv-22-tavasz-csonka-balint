package gameclub.service;

import gameclub.domain.Game;
import gameclub.domain.Group;
import gameclub.domain.JoinRequest;
import gameclub.persistence.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;


@Service
public class GameClubService {

    @Autowired
    private DataStore dataStore;

    @Autowired
    public IdentityManager identityManager;

    public boolean VerifyLogin(String loginName, String loginPassword) {

        if (dataStore.GetPlayer(loginName) != null && dataStore.GetPlayer(loginName).getPassword().equals(loginPassword)){
            return true;
        }
        else{
            return false;
        }
    }

    public void SetCurrentPLayer(String loginName, String loginPassword){
        identityManager.setCurrentPLayer(dataStore.GetPlayer(loginName, loginPassword));
    }

    public HashMap<Long, String> ListGames(){
        HashMap<Long, String> gameList = new HashMap<>();
        for (Game game : dataStore.GetGames()){
            gameList.put(game.getId(),game.getName());
        }
        return gameList;
    }

    public void AddGame(long gameID){
        if (!identityManager.getCurrentPLayer().getGames().stream().anyMatch(g -> g.getId() == gameID)){
            identityManager.getCurrentPLayer().getGames().add(dataStore.GetGame(gameID));
        }
    }

    public HashMap<Long, String> ListGroups(){
        HashMap<Long, String> groupList = new HashMap<>();
        for (Group group : dataStore.GetGroups()){
            groupList.put(group.getId(),group.getName());
        }
        return groupList;
    }

    public HashMap<Long, String> ListJoinRequests(){
        HashMap<Long, String> joinRequestsList = new HashMap<>();
        for (JoinRequest joinRequest : dataStore.GetJoinRequests()){
            joinRequestsList.put(joinRequest.getPlayer().getId(),joinRequest.getPlayer().getName());
        }
        return joinRequestsList;
    }

    public void CreateJoinRequest(long groupID){
        dataStore.CreateJoinRequest(groupID,identityManager.getCurrentPLayer().getId());
    }

}
