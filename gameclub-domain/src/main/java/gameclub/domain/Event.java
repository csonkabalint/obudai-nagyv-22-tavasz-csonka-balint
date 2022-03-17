package gameclub.domain;

import java.nio.file.attribute.GroupPrincipal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {

    long id;
    LocalDateTime date;
    String place;
    ArrayList<Player> participants;
    Group group;

}
