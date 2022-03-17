package gameclub.dto;

import gameclub.domain.JoinRequestState;

public class JoinRequestDTO {

    JoinRequestState state;
    int group;
    int player;

    public JoinRequestState getState() {
        return state;
    }

    public void setState(JoinRequestState state) {
        this.state = state;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public JoinRequestDTO() {
    }
}
