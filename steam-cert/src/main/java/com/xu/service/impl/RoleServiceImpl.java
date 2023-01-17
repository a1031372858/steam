package com.xu.service.impl;

import com.xu.bean.Role;
import com.xu.mapper.RoleMapper;
import com.xu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> selectRoleListByUserId(Long userId) {
        List<Role> result = new ArrayList<>();
        if(userId==null)return result;
        result=roleMapper.selectRoleListByUserId(userId);
        return result;
    }
}
