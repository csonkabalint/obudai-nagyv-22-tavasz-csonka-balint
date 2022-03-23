package gameclub.dto;

import gameclub.domain.JoinRequestState;

public class JoinRequestDTO {

    JoinRequestState state;
    int groupId;
    int userId;

    public JoinRequestState getState() {
        return state;
    }

    public void setState(JoinRequestState state) {
        this.state = state;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public JoinRequestDTO() {
    }
}
