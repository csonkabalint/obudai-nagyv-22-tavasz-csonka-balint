package gameclub.dto;

import gameclub.domain.Event;
import gameclub.domain.Player;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventDTO {

    long id;
    String date;
    String location;
    List<String> participants;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public EventDTO(Event event) {
        this.id = event.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.date = event.getDate().format(formatter);
        this.location = event.getPlace();
        this.participants = new ArrayList<>();
        for(Player p : event.getParticipants()){
            participants.add(p.getName());
        }
    }

    public EventDTO() {
    }
}
