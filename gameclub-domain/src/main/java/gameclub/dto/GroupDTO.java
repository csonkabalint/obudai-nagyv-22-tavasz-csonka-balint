package gameclub.dto;

import gameclub.domain.Event;
import gameclub.domain.Group;
import gameclub.domain.Player;

import java.util.ArrayList;
import java.util.List;

public class GroupDTO {

    long id;
    String name;
    String admin;
    int noOfMembers;
    boolean hasRequested;
    List<PlayerDTO> members;
    List<EventDTO> events;


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

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getNoOfMembers() {
        return noOfMembers;
    }

    public void setNoOfMembers(int noOfMembers) {
        this.noOfMembers = noOfMembers;
    }

    public boolean isHasRequested() {
        return hasRequested;
    }

    public void setHasRequested(boolean hasRequested) {
        this.hasRequested = hasRequested;
    }

    public List<PlayerDTO> getMembers() {
        return members;
    }

    public void setMembers(List<PlayerDTO> members) {
        this.members = members;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }

    public GroupDTO(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.admin = group.getAdmin().getName();
        this.noOfMembers = group.getMembers().size();

        members = new ArrayList<>();

        for (Player player:  group.getMembers())
        {
            members.add(new PlayerDTO(player));
        }


        events = new ArrayList<>();

        for (Event event:  group.getEvents())
        {
            events.add(new EventDTO(event));
        }

    }
}
