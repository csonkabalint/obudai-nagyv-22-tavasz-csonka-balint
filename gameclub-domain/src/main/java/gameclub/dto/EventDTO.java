package gameclub.dto;

import gameclub.domain.Event;
import gameclub.domain.Player;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventDTO {

    long id;

    @NotNull
    @InFuture
    String date;

    @NotBlank
    String location;

    @NotBlank
    String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        this.description = event.getDescription();
        this.participants = new ArrayList<>();
        for(Player p : event.getParticipants()){
            participants.add(p.getName());
        }
    }

    public EventDTO() {
        this.participants = new ArrayList<>();
    }
}
