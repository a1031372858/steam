package com.xu.bean.custom;

import com.xu.bean.Authority;
import com.xu.bean.Role;
import com.xu.bean.UserDetail;

import java.io.Serializable;
import java.util.List;

public class UserAuthorityBo implements Serializable {
    private UserDetail userDetail;
    private List<Role> roleList;
    private List<Authority> authorityList;

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Authority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
    }
}
