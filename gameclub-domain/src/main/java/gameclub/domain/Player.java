package gameclub.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Player extends User {

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Game> games;

    public Player(String loginName, String name, String password, String email, List<Role> roles, List<Game> games) {
        super(loginName, name, password, email, roles);
        this.games = games;
    }

    public Player() {

    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }



}
