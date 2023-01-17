package com.xu.service;

import com.xu.bean.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> selectRoleListByUserId(Long userId);
}
