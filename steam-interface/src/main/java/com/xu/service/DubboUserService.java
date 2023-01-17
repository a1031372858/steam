package com.xu.service;

import com.xu.bean.UserDetail;
import com.xu.bean.custom.ServiceResult;

/**
 * @author kyo
 * @Date 2021/10/10 - 11:39
 */
public interface DubboUserService {

    public void printUser(UserDetail userDetail);

    ServiceResult<UserDetail> loginByPassword(String phone, String userPassword);
}
