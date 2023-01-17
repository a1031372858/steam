package com.xu.service;

import com.xu.bean.Authority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorityService {

    List<Authority> selectAuthorityListByRoleIdList(List<Long> roleIdList);
}
