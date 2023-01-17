package com.xu.service;

import com.xu.bean.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    int addGold(int userId,int gold);

    void insertAccount(Account account);

    void deleteAccount(int userId);
}
