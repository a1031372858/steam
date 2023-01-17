package com.xu.service.impl;

import com.xu.bean.Account;
import com.xu.mapper.AccountMapper;
import com.xu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Override
    public boolean AddGoldOfAccount(long userId, BigDecimal gold) {
        accountMapper.updateAccountGold(userId,gold);
        return false;
    }

    @Override
    public Account selectAccountByUserId(long userId) {
        return accountMapper.selectAccountByUserId(userId);
    }
}
