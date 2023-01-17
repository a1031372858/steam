package com.xu.service;

import com.xu.bean.UserDetail;
import com.xu.bean.custom.ServiceResult;
import com.xu.bean.custom.UserAuthorityBo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public void printUser(UserDetail userDetail);

    ServiceResult<UserDetail> loginByPassword(String phone, String userPassword);

    public UserDetail getUserByUserId(long userId);

    public UserDetail getUserByPhone(String phone);

    UserAuthorityBo selectUserAuthorityBoByPhone(String phone);
}
