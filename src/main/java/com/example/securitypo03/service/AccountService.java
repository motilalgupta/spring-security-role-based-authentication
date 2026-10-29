package com.example.securitypo03.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @PreAuthorize("hasRole('USER')")
    public String getBalance(){
        return "you balance is : 80000";
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String closeAccount(){
        return "Account closed!";
    }
}
