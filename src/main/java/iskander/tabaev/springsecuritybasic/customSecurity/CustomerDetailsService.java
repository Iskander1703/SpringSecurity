package iskander.tabaev.springsecuritybasic.customSecurity;


import iskander.tabaev.springsecuritybasic.models.Customer;
import iskander.tabaev.springsecuritybasic.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "CustomerDetailsService")
public class CustomerDetailsService implements UserDetailsService {



    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Customer> customers = customerRepository.findByEmail(username);
        if (customers.size() == 0) {
            throw new UsernameNotFoundException("Пользователь с mail: " + username + " не найден");
        }

        return new SecurityCustomer(customers.get(0));
    }
}
