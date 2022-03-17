package gameclub.domain;

public class JoinRequest {

    JoinRequestState state;
    Group group;
    Player player;

    public JoinRequestState getState() {
        return state;
    }

    public void setState(JoinRequestState state) {
        this.state = state;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public JoinRequest() {
    }

    enum JoinRequestState{
        REQUESTED,
        ACCEPTED,
        REJECTED
    }
}
