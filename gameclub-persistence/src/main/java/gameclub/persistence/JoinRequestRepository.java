package gameclub.persistence;

import gameclub.domain.JoinRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JoinRequestRepository extends CrudRepository<JoinRequest, Long> {

    public List<JoinRequest> findAll();
}
