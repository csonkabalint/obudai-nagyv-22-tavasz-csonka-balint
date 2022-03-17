package gameclub.domain;

import java.util.ArrayList;

public class Player extends User {

    //ArrayList<Game> games;
    ArrayList<Integer> games;

    public ArrayList<Integer> getGames() {
        return games;
    }

    public void setGames(ArrayList<Integer> games) {
        this.games = games;
    }

    public Player() {

    }
}
