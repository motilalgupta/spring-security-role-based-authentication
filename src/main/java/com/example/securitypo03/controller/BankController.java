package com.example.securitypo03.controller;

import com.example.securitypo03.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BankController {

    @Autowired
    AccountService accountService;

    @GetMapping("/balance")                 // user
    public String checkBalance(){
        return accountService.getBalance();
    }

    @GetMapping("/close")                  // admin
    public String closeAccount(){
        return accountService.closeAccount();
    }

    @GetMapping("/about")
    public String about(){
        return "Please wait, we are having lunch";
    }


}
