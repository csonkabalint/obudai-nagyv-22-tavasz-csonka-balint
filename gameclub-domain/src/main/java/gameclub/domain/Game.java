package gameclub.domain;

import java.util.ArrayList;

public class Game {

    long id;
    String name;
    String description;
    int minimumAge;
    Limits playTime;
    Limits numberOfPlayers;
    ArrayList<Category> categories;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Limits getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Limits playTime) {
        this.playTime = playTime;
    }

    public Limits getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Limits numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public Game() {
    }

    enum Category{
        FANTASY,
        MYTHOLOGY,
        WAR,
        STRATEGY,
        BUILDING
    }
}