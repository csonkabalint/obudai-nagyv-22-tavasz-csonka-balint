package gameclub.domain;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
public class JoinRequestId implements Serializable {

    @OneToOne
    Player player;

    @OneToOne
    Group group;

}
