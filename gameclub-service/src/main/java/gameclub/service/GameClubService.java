package gameclub.service;

import gameclub.persistence.DataStore;

public class GameClubService {

    DataStore dataStore;

    public GameClubService() {
        this.dataStore = new DataStore();
        Init();
    }

    public void Init() {
        dataStore.InitializeData();
    }

}
