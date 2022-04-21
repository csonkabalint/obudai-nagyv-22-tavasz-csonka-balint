package gameclub.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    long id;

    String name;
    //Player admin;
    @OneToOne
    Player admin;

    @ManyToMany
    List<Player> members;

    @OneToMany
    List<Event> events;

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

   public Player getAdmin() {
        return admin;
    }

    public void setAdmin(Player admin) {
        this.admin = admin;
    }

    public List<Player> getMembers() {
        return members;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Group() {
    }

    public Group(String name, Player admin, List<Player> members, List<Event> events) {
        this.name = name;
        this.admin = admin;
        this.members = members;
        this.events = events;
    }
}
