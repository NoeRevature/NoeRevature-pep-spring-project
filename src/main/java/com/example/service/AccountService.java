package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account){
        if(account.getUsername().isEmpty()) return null;
        if(account.getPassword().length() < 4) return null;
        if(findAccountByUsername(account.getUsername()) != null) return new Account("", "-1");

        return accountRepository.save(account);
    }

    public Account findAccountByUsernameAndPassword(Account account){
        return accountRepository.findAccountByUsernameAndPassword(
            account.getUsername(), account.getPassword());
    }

    public Account findAccountByUsername(String username){
        return accountRepository.findAccountByUsername(username);
    }
}
