package gameclub.domain;

import javax.persistence.*;


@Entity
public class JoinRequest {

    @Id
    JoinRequestId id;

    JoinRequestState state;


    public JoinRequestState getState() {
        return state;
    }

    public void setState(JoinRequestState state) {
        this.state = state;
    }

   public Group getGroup() {
        return id.getGroup();
    }

    public void setGroup(Group group) {
        this.id.setGroup(group);
    }

    public Player getPlayer() {
        return id.getPlayer();
    }

    public void setPlayer(Player player) {
        this.id.setPlayer(player);
    }

    public JoinRequest() {
    }

    public JoinRequest(JoinRequestState state, Group group, Player player) {
        id = new JoinRequestId();
        this.state = state;
        this.id.setGroup(group);
        this.id.setPlayer(player);
    }


}

