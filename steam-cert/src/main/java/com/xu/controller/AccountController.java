package com.xu.controller;

import com.xu.bean.Account;
import com.xu.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("accounts")
@RestController
public class AccountController {

    private AccountService accountService;

    @GetMapping("user/{userId}")
    public Account getUserDetailByUserId(@PathVariable long userId){
        Account account =accountService.selectAccountByUserId(userId);
        return account;
    }
}
