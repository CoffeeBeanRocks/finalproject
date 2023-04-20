package com.example.c322.finalproject.controllers;

import com.example.c322.finalproject.models.Login;
import com.example.c322.finalproject.repositories.AccountRepository;
import com.example.c322.finalproject.repositories.LoginRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private LoginRepository loginRepository;
    private AccountRepository accountRepository;

    public AccountController(LoginRepository loginRepository, AccountRepository accountRepository) {
        this.loginRepository = loginRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/valid/{email}/{password}")
    public boolean validLogin(@PathVariable String email, @PathVariable String password){
        List<Login> maybeLogin = loginRepository.findByEmail(email);
        if(!maybeLogin.isEmpty()) {
            Login login = maybeLogin.get(0);
            if(login.validCredentials(email, password))
                return true;
            throw new IllegalStateException("Username or password is incorrect!");
        }
        else
            throw new IllegalStateException("Account not found!");
    }
}
