package com.xu.mapper;

import com.xu.bean.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {

    public int updateAccountGold(long userId,int gold);

    public Account selectAccountByUserId(long userId);
}
