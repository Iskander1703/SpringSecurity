package iskander.tabaev.springsecuritybasic.controllers;
import iskander.tabaev.springsecuritybasic.models.Accounts;
import iskander.tabaev.springsecuritybasic.models.Customer;
import iskander.tabaev.springsecuritybasic.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountsRepository accountsRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails() {
      Accounts accounts=new Accounts();
      accounts.setAccountNumber(228);
      accounts.setAccountType("хуй");
      accounts.setBranchAddress("ksdflsdfl");
      accounts.setAccountType("пиздец");
      return accounts;
    }

}