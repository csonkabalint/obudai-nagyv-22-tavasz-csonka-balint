package gameclub.domain;

import java.util.ArrayList;

public class Player extends User {

    ArrayList<Game> games;

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public Player() {

    }
}
