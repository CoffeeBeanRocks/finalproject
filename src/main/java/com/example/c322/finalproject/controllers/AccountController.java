package com.example.c322.finalproject.controllers;

import com.example.c322.finalproject.models.Login;
import com.example.c322.finalproject.repositories.AccountRepository;
import com.example.c322.finalproject.repositories.LoginRepository;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/transfer/{recipientEmail}/{myEmail}/{myPassword}/{amount}")
    public void transfer(@PathVariable String recipientEmail, @PathVariable String myEmail, @PathVariable String myPassword, @PathVariable double amount) {
        // Validate the recipient email

        // Validate user login information and retrieve user bank account information
        List<Login> maybeLogin = loginRepository.findByEmail(myEmail);
        if (maybeLogin == null) {
            throw new IllegalArgumentException("Invalid login information!");
        }


        userBankAccount.setBalance(userBalance - amount);
        accountRepository.save(userBankAccount);

        // Add amount to recipient's bank account balance and update in the database
        User recipient = accountRepository.findByEmail(recipientEmail);
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient email not found");
        }

        BankAccount recipientBankAccount = recipient.getBankAccount();
        if (recipientBankAccount == null) {
            throw new IllegalArgumentException("Recipient has no bank account");
        }

        double recipientBalance = recipientBankAccount.getBalance();
        recipientBankAccount.setBalance(recipientBalance + amount);
        accountRepository.save(recipientBankAccount);
    }



        //TODO: Validate user login information
        //TODO: Validate recipientEmail
        //TODO: Update users bank accounts
    }
}
