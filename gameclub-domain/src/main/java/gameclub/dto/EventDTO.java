package gameclub.dto;

import gameclub.domain.Event;

import java.time.format.DateTimeFormatter;

public class EventDTO {

    long id;
    String date;
    String location;

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

    public EventDTO(Event event) {
        this.id = event.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.date = event.getDate().format(formatter);
        this.location = event.getPlace();
    }
}
