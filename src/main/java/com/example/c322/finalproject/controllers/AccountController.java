package com.example.c322.finalproject.controllers;

import com.example.c322.finalproject.models.Account;
import com.example.c322.finalproject.models.Login;
import com.example.c322.finalproject.models.ProxyAccount;
import com.example.c322.finalproject.repositories.AccountRepository;
import com.example.c322.finalproject.repositories.LoginRepository;
import com.example.c322.finalproject.repositories.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private LoginRepository loginRepository;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public AccountController(LoginRepository loginRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.loginRepository = loginRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
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

    @PostMapping("/transfer/{recipientEmail}/{myEmail}/{myPassword}/{amount}")
    public void transfer(@PathVariable String recipientEmail, @PathVariable String myEmail, @PathVariable String myPassword, @PathVariable double amount) {
        if(validLogin(myEmail, myPassword))
        {
            List<Login> maybeLogin = loginRepository.findByEmail(recipientEmail);
            if(maybeLogin.isEmpty())
                throw new IllegalStateException("Recipient Account Not Found!");

            Account recipientAccount = maybeLogin.get(0).getAccount();
            Account senderAccount = loginRepository.findByEmail(myEmail).get(0).getAccount();

            ProxyAccount myProxy = new ProxyAccount(senderAccount, transactionRepository);

            myProxy.transferMoney(recipientAccount, amount);
        }
        throw new IllegalStateException("Invalid Login Credentials!");
    }

}
