package gameclub.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue
    long id;

   String name;

    String description;

    int minimumAge;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "min", column = @Column(name = "MIN_LENGTH")),
            @AttributeOverride(name = "max", column = @Column(name = "MAX_LENGTH"))
    })
    Limits playTime;

    @Embedded
    Limits numberOfPlayers;

    @ElementCollection
    List<Category> categories;

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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Game() {
    }

    @Override
    public String toString(){
        return "-\t id: " + id +
                "\n\t name: " + name +
                "\n\t description: " + description  +
                "\n\t categories: "  + categories.toString() +
                "\n\t minimum age: " + minimumAge +
                "\n\t number of players: " + numberOfPlayers.getMin() + "-" + numberOfPlayers.getMax() +
                "\n\t play time: " + playTime.getMin() + "-" + playTime.getMax();
    }


}

