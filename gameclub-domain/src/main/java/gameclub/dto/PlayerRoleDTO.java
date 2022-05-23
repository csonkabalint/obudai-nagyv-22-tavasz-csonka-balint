package gameclub.dto;

import gameclub.domain.Role;

public class PlayerRoleDTO {

    String role;

    public String getRole() {
        return role;
    }


    public PlayerRoleDTO(Role role) {
        this.role = role.toString();
    }
}
