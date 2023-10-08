package iskander.tabaev.springsecuritybasic.repositories;

import iskander.tabaev.springsecuritybasic.models.Cards;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CardsRepository extends CrudRepository<Cards, Long> {
	
	List<Cards> findByCustomerId(long customerId);

}
