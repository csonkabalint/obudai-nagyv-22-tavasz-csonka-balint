package gameclub.persistence;


import gameclub.domain.Game;
import gameclub.domain.JoinRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface GameRepository extends CrudRepository<Game, Long> {

    public List<Game> findAll();


}
