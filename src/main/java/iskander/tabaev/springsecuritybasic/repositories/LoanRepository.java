package iskander.tabaev.springsecuritybasic.repositories;

import iskander.tabaev.springsecuritybasic.models.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends CrudRepository<Loans, Long> {
	
	List<Loans> findByCustomerIdOrderByStartDtDesc(Long customerId);

}
