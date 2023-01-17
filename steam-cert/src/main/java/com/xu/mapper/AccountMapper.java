package com.xu.mapper;

import com.xu.bean.Account;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

@Mapper
public interface AccountMapper {

    public int updateAccountGold(long userId, BigDecimal gold);

    public Account selectAccountByUserId(long userId);
}
