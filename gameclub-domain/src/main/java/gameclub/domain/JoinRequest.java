package gameclub.domain;

import javax.persistence.*;


@Entity
public class JoinRequest {

   /* @Id
    @GeneratedValue
    long id;*/

    @Id
    JoinRequestId id;

    JoinRequestState state;

    /*@OneToOne
    Group group;
    @OneToOne
    Player player;*/

    public JoinRequestState getState() {
        return state;
    }

    public void setState(JoinRequestState state) {
        this.state = state;
    }

   /* public Group getGroup() {
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

    public JoinRequest(JoinRequestState state, Group group, Player player) {
        this.state = state;
        this.group = group;
        this.player = player;
    }*/
}

