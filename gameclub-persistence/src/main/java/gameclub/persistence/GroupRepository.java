package gameclub.persistence;

import gameclub.domain.Game;
import gameclub.domain.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface GroupRepository extends CrudRepository<Group, Long> {

    public List<Group> findAll();

}
