package com.example.c322.finalproject.controllers;

import com.example.c322.finalproject.models.*;
import com.example.c322.finalproject.models.Observer;
import com.example.c322.finalproject.repositories.AccountRepository;
import com.example.c322.finalproject.repositories.LoginRepository;
import com.example.c322.finalproject.repositories.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping()
public class AccountController {

    private LoginRepository loginRepository;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    private Subject subject;

    public AccountController(LoginRepository loginRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.loginRepository = loginRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.subject = new Subject();
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    @GetMapping("/valid/{email}/{password}")
    public int validLogin(@PathVariable String email, @PathVariable String password){
        Optional<Login> maybeLogin = loginRepository.findByEmail(email);
        if(maybeLogin.isPresent()) {
            Login login = maybeLogin.get();
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

        Optional<Login> maybeLogin = loginRepository.findByEmail(recipientEmail);
        if(maybeLogin.isEmpty())
            throw new IllegalStateException("Recipient Account Not Found!");

        Account recipientAccount = maybeLogin.get().getAccount();
        Account senderAccount = getAccountFromId(senderId);

        ProxyAccount myProxy = new ProxyAccount(senderAccount, transactionRepository);

        myProxy.transferMoney(recipientAccount, amount);

        subject.notify(recipientEmail);
        subject.notify(myEmail);
    }

    @GetMapping("/find/id/{accountId}")
    public Account getAccountFromId(@PathVariable int accountId) {
        Optional<Account> maybeAccount = accountRepository.findById(accountId);
        if(maybeAccount.isPresent()) {
            return maybeAccount.get();
        }
        else
            throw new IllegalStateException("Account not found!");
    }

    @GetMapping("/find/email/{email}")
    public Account getAccountFromEmail(@PathVariable String email) {
        Optional<Login> maybeLogin = loginRepository.findByEmail(email);
        if(maybeLogin.isPresent()) {
            return maybeLogin.get().getAccount();
        }
        else
            throw new IllegalStateException("Account not found!");
    }

    @PostMapping("/create/{email}/{password}/{sendEmail}")
    public int createLogin(@PathVariable String email, @PathVariable String password, @PathVariable boolean sendEmail) {

        if(loginRepository.findByEmail(email).isEmpty())
        {
            Account account = new Account();
            account.setSendEmail(sendEmail);
            account.setBalance(100);
            accountRepository.save(account);

            if(sendEmail) {
                subject.subscribe(email, new TransferObserver());
            }

            Login login = new Login();
            login.setEmail(email);
            login.setPassword(password);
            login.setAccount(account);
            loginRepository.save(login);

            return account.getId();
        }
        throw new IllegalStateException("That Email Is Already Associated With An Account!");
    }

    @PutMapping("/update/emails")
    public void updateObservers() {
        List<Account> sendEmailAccounts = accountRepository.findBySendEmail(true);
        Map<String, Observer> observerList = new HashMap<>();
        for(Account acc : sendEmailAccounts) {
            if(acc.isSendEmail()) {
                Optional<Login> maybeLogin = loginRepository.findByAccount(acc);
                if(maybeLogin.isPresent())
                    observerList.put(maybeLogin.get().getEmail(), new TransferObserver());
            }
        }
        subject.setObservers(observerList);
    }

    @PutMapping("/update/notification/{email}/{emailPreference}")
    public void updateNotificationPreference(@PathVariable String email, @PathVariable boolean emailPreference) {
        Optional<Login> maybeLogin = loginRepository.findByEmail(email);
        if(maybeLogin.isEmpty())
            throw new IllegalStateException("Email not found!");
        Account updatedAccount = maybeLogin.get().getAccount();
        updatedAccount.setSendEmail(emailPreference);
        accountRepository.save(updatedAccount);
    }
}
