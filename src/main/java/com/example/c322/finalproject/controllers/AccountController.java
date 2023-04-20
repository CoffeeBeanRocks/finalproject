package com.example.c322.finalproject.controllers;

import com.example.c322.finalproject.models.Account;
import com.example.c322.finalproject.models.Login;
import com.example.c322.finalproject.models.ProxyAccount;
import com.example.c322.finalproject.repositories.AccountRepository;
import com.example.c322.finalproject.repositories.LoginRepository;
import com.example.c322.finalproject.repositories.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public int validLogin(@PathVariable String email, @PathVariable String password){
        List<Login> maybeLogin = loginRepository.findByEmail(email);
        if(!maybeLogin.isEmpty()) {
            Login login = maybeLogin.get(0);
            if(login.validCredentials(email, password))
                return login.getAccount().getId();
            throw new IllegalStateException("Username or password is incorrect!");
        }
        else
            throw new IllegalStateException("Account not found!");
    }

    @PutMapping("/transfer/{recipientEmail}/{myEmail}/{myPassword}/{amount}")
    public void transfer(@PathVariable String recipientEmail, @PathVariable String myEmail, @PathVariable String myPassword, @PathVariable double amount) {
        int senderId = validLogin(myEmail, myPassword);

        List<Login> maybeLogin = loginRepository.findByEmail(recipientEmail);
        if(maybeLogin.isEmpty())
            throw new IllegalStateException("Recipient Account Not Found!");

        Account recipientAccount = maybeLogin.get(0).getAccount();
        Account senderAccount = getAccount(senderId);

        ProxyAccount myProxy = new ProxyAccount(senderAccount, transactionRepository);

        myProxy.transferMoney(recipientAccount, amount);
    }

    @GetMapping("/find/{accountId}")
    public Account getAccount(@PathVariable int accountId) {
        Optional<Account> maybeAccount = accountRepository.findById(accountId);
        if(maybeAccount.isPresent()) {
            return maybeAccount.get();
        }
        else
            throw new IllegalStateException("Account not found!");
    }

    @PostMapping("/create/{email}/{password}/{sendEmail}")
    public int createAccount(@PathVariable String email, @PathVariable String password, @PathVariable boolean sendEmail) {
        if(loginRepository.findByEmail(email).isEmpty())
        {
            Account account = new Account();
            account.setSendEmail(sendEmail);
            account.setBalance(100);
            accountRepository.save(account);

            Login login = new Login();
            login.setEmail(email);
            login.setPassword(password);
            login.setAccount(account);
            loginRepository.save(login);
        }
        throw new IllegalStateException("That Email Is Already Associated With An Account!");
    }

    //TODO: Send email

    //TODO: Post account notification preferences
}
