package com.xu.service.impl;

import com.xu.bean.Authority;
import com.xu.mapper.AuthorityMapper;
import com.xu.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;
    @Override
    public List<Authority> selectAuthorityListByRoleIdList(List<Long> roleIdList) {
        List<Authority> authorityList = Collections.emptyList();
        if(roleIdList==null||roleIdList.size()<=0)return authorityList;
        authorityList = authorityMapper.selectAuthorityListByRoleIdList(roleIdList);
        return authorityList;
    }
}
