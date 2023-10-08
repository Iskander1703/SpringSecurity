package iskander.tabaev.springsecuritybasic.repositories;

import iskander.tabaev.springsecuritybasic.models.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
	
	
}
