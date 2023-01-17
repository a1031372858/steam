package com.xu.controller;

import com.sun.istack.internal.NotNull;
import com.xu.bean.Authority;
import com.xu.bean.Role;
import com.xu.bean.UserDetail;
import com.xu.bean.custom.UserAuthorityBo;
import com.xu.mapper.AuthorityMapper;
import com.xu.mapper.RoleMapper;
import com.xu.service.AuthorityService;
import com.xu.service.RoleService;
import com.xu.service.UserService;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RequestMapping("users")
@RestController
public class UserDetailController {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailController.class);

    @Autowired
    private UserService userService;

    @GetMapping("user/{userId}")
    public UserDetail getUserDetailByUserId( @PathVariable long userId){
        UserDetail user =userService.getUserByUserId(userId);
        return user;
    }

    @PostMapping("phone")
    public UserDetail getUserDetailByPhone(@RequestBody @NotNull UserDetail userDetail){
        UserDetail user =userService.getUserByPhone(userDetail.getPhone());
        return user;
    }

    /**
     * 根据手机号获得用户信息，角色信息，权限信息
     * @param param
     * @return
     */
    @PostMapping("authority")
    public UserAuthorityBo getUserAuthority(@RequestBody @NotNull UserDetail param){
        return userService.selectUserAuthorityBoByPhone(param.getPhone());
    }
}
