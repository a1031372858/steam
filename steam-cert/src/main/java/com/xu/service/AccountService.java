package com.xu.service;

import com.xu.bean.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface AccountService {
    boolean AddGoldOfAccount(long userId, BigDecimal gold);

    Account selectAccountByUserId(long userId);
}
