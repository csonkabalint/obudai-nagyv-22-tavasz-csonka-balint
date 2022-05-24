package gameclub.dto;

import gameclub.domain.JoinRequest;
import gameclub.domain.JoinRequestState;

public class JoinRequestDTO {

    String state;
    String playerName;
    long groupId;
    long userId;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public JoinRequestDTO(JoinRequest joinRequest) {
        this.state = joinRequest.getState().name();
        this.groupId = joinRequest.getGroup().getId();
        this.userId = joinRequest.getPlayer().getId();
        this.playerName = joinRequest.getPlayer().getName();
    }
}

