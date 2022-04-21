package gameclub.persistence;

import gameclub.domain.JoinRequest;
import gameclub.domain.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PlayerRepository extends CrudRepository<Player, Long> {

    public Player findByLoginName(String loginName);

    public Player findByLoginNameAndPassword(String loginName, String password);

    public List<Player> findAll();

}
