package gameclub.persistence;

import gameclub.domain.Group;
import gameclub.domain.JoinRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JoinRequestRepository extends CrudRepository<JoinRequest, Long> {

    public List<JoinRequest> findAll();

}
