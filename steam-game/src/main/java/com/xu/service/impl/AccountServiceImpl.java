package com.xu.service.impl;

import com.xu.bean.Account;
import com.xu.mapper.AccountMapper;
import com.xu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Override
    public int addGold(int userId, int gold) {
        return accountMapper.updateAccountGold(userId,gold);
    }

    @Override
    public void insertAccount(Account account) {

    }

    @Override
    public void deleteAccount(int userId) {

    }
}
