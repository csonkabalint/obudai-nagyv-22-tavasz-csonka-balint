package gameclub.dto;

import gameclub.domain.Category;
import gameclub.domain.Game;
import gameclub.domain.Limits;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class GameDTO {

    long id;

    String name;
    
    String description;

    int minimumAge;

    int playTimeMax;
    int playTimeMin;

    int numberOfPlayersMax;
    int numberOfPlayersMin;
    
    List<String> categories;

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

    public int getPlayTimeMax() {
        return playTimeMax;
    }

    public void setPlayTimeMax(int playTimeMax) {
        this.playTimeMax = playTimeMax;
    }

    public int getPlayTimeMin() {
        return playTimeMin;
    }

    public void setPlayTimeMin(int playTimeMin) {
        this.playTimeMin = playTimeMin;
    }

    public int getNumberOfPlayersMax() {
        return numberOfPlayersMax;
    }

    public void setNumberOfPlayersMax(int numberOfPlayersMax) {
        this.numberOfPlayersMax = numberOfPlayersMax;
    }

    public int getNumberOfPlayersMin() {
        return numberOfPlayersMin;
    }

    public void setNumberOfPlayersMin(int numberOfPlayersMin) {
        this.numberOfPlayersMin = numberOfPlayersMin;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public GameDTO(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.description = game.getDescription();
        this.minimumAge = game.getMinimumAge();
        this.playTimeMax = game.getPlayTime().getMax();
        this.playTimeMin = game.getPlayTime().getMin();
        this.numberOfPlayersMax = game.getNumberOfPlayers().getMax();
        this.numberOfPlayersMin = game.getNumberOfPlayers().getMin();
        categories = new ArrayList<>();
        for (Category category : game.getCategories()) {
            this.categories.add(category.toString());
        }
    }

    public GameDTO(){}
}
