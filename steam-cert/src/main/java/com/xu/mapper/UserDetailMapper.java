package com.xu.mapper;

import com.xu.bean.UserDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDetailMapper {

    public UserDetail selectUser();

    public UserDetail selectUserByPhoneAndPassword(UserDetail user);

    public UserDetail selectUserByPhoneAndPassword2(@Param("phone") String phone,@Param("password") String password);

    public UserDetail selectUserByUserId(Long userId);

    public int selectUserCountByPhone(String phone);

    public UserDetail selectUserByPhone(String phone);

    public int insertUser(UserDetail user);

    public int updateUserName(@Param("userId") Long userId,@Param("displayName")  String displayName);
}
