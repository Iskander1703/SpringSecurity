package iskander.tabaev.springsecuritybasic.repositories;

import iskander.tabaev.springsecuritybasic.models.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {
	
	Accounts findByCustomerId(Long customerId);

}
