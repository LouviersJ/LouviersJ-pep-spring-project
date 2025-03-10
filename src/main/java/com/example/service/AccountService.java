package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.*;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    public Account findByUsername(String username){
        return accountRepository.findByUsername(username);
    }

    public boolean checkAccount(String username, String password){
        return accountRepository.existsByUsername(username) && accountRepository.existsByPassword(password);
    }

    public Account addAccount(Account account){
        return accountRepository.save(account);
    }

}
