package iskander.tabaev.springsecuritybasic.controllers;


import iskander.tabaev.springsecuritybasic.models.Customer;
import iskander.tabaev.springsecuritybasic.models.Loans;
import iskander.tabaev.springsecuritybasic.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    @Autowired
    private LoanRepository loanRepository;

    @PostMapping("/myLoans")
    //@PostAuthorize("returnObject.size()>3")
   // @PostAuthorize("returnObject.size()>4")
   // @PostFilter("filterObject.loanType.equals('Home')")
    public List<Loans> getLoanDetails(@RequestBody Customer customer) {
        List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getId());
        if (loans != null ) {
            return loans;
        }else {
            return null;
        }
    }

}