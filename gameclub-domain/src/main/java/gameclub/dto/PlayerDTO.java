package gameclub.dto;

import gameclub.domain.Player;
import gameclub.domain.Role;
import gameclub.domain.User;
import java.util.ArrayList;
import java.util.List;

public class PlayerDTO {


    String name;
    String email;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public PlayerDTO(Player player) {
        this.name = player.getName();
        this.email = player.getEmail();
    }
}
