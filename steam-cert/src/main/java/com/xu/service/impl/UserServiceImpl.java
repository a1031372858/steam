package com.xu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.xu.bean.Authority;
import com.xu.bean.Role;
import com.xu.bean.UserDetail;
import com.xu.bean.custom.ServiceResult;
import com.xu.bean.custom.UserAuthorityBo;
import com.xu.mapper.UserDetailMapper;
import com.xu.service.AuthorityService;
import com.xu.service.RoleService;
import com.xu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kyo
 * @Date 2021/10/10 - 11:44
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDetailMapper userDetailMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private UserService userService;

    @Override
    public void printUser(UserDetail userDetail) {
        if(userDetail==null){
            logger.info("参数为null");
            return;
        }
        logger.info("userId:"+userDetail.getUserId());
    }

    @Override
    public ServiceResult<UserDetail> loginByPassword(String phone, String userPassword) {
        ServiceResult<UserDetail> result = new ServiceResult<>();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(userPassword)) {
            result.setStatus(2);
            logger.info("用户名或密码为空");
            result.setMessage("用户名或密码为空");
            return result;
        }

        //检查手机号密码是否正确
        UserDetail userDetail = new UserDetail();
        userDetail.setPhone(phone);
        userDetail.setUserPassword(userPassword);
        userDetail = userDetailMapper.selectUserByPhoneAndPassword(userDetail);

        if(null !=userDetail){
            result.setStatus(1);
            result.setResult(userDetail);
            result.setMessage("成功");
        }else{
            result.setStatus(2);
            result.setMessage("手机号或密码错误");
        }

        return result;
    }

    @Override
    public UserDetail getUserByUserId(long userId) {
        return userDetailMapper.selectUserByUserId(userId);
    }

    @Override
    public UserDetail getUserByPhone(String phone) {
        return userDetailMapper.selectUserByPhone(phone);
    }

    @Override
    public UserAuthorityBo selectUserAuthorityBoByPhone(String phone){
        UserAuthorityBo result = new UserAuthorityBo();
        if(StringUtils.isEmpty(phone))return result;
        UserDetail userDetail =userService.getUserByPhone(phone);
        if(userDetail==null){
            return null;
        }
        result.setUserDetail(userDetail);

        List<Role> roleList =roleService.selectRoleListByUserId(userDetail.getUserId());
        result.setRoleList(roleList);

        List<Long> roleIdList = new ArrayList<>();
        roleList.forEach(o->{
            roleIdList.add(o.getRoleId());
        });
        List<Authority> authorityList = authorityService.selectAuthorityListByRoleIdList(roleIdList);
        result.setAuthorityList(authorityList);

        return result;
    }
}
