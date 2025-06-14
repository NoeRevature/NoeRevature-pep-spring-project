package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    Account findAccountByUsernameAndPassword(String username, String password);
    
    Account findAccountByUsername(String username);

    Account findAccountByAccountId(int accountId);
}
