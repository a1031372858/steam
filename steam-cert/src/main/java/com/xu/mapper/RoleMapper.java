package com.xu.mapper;

import com.xu.bean.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<Role> selectRoleListByUserId(Long userId);
}
