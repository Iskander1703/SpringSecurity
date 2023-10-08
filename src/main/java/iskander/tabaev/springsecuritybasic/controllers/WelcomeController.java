package iskander.tabaev.springsecuritybasic.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/welcome")
    public String sayWlecome(){
        return "Welcome from Spring Application with  Security";
    }
}
