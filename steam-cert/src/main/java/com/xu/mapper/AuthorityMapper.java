package com.xu.mapper;

import com.xu.bean.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthorityMapper {
    List<Authority> selectAuthorityListByRoleId(Long roleId);

    List<Authority> selectAuthorityListByRoleIdList(@Param("roleList") List<Long> roleList);
}
