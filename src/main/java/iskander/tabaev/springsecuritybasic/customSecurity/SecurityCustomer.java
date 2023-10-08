package iskander.tabaev.springsecuritybasic.customSecurity;

import iskander.tabaev.springsecuritybasic.models.Authorities;
import iskander.tabaev.springsecuritybasic.models.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SecurityCustomer implements UserDetails {

    private final Customer customer;

    public SecurityCustomer(Customer customer) {
        this.customer = customer;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Проходим по каждой роли в Set и добавляем ее в authorities
        for (Authorities authority : customer.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return customer.getPwd();
    }

    @Override
    public String getUsername() {
        return customer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
