package gameclub.persistence;

import gameclub.domain.Game;
import gameclub.domain.Group;
import gameclub.domain.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface GroupRepository extends CrudRepository<Group, Long> {

    public List<Group> findAll();

    public List<Group> findByMembers(Player member);

    public Group findByAdmin(Player admin);

}
