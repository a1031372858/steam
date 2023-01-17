package com.xu;

import com.xu.bean.UserDetail;
import com.xu.mapper.UserDetailMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CertificateApplication.class)
public class MybatisTest {

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void selectUser(){
        SqlSession sqlSession=sqlSessionFactory.openSession(true);
        UserDetailMapper testMapper = sqlSession.getMapper(UserDetailMapper.class);
        UserDetail param = new UserDetail();
        param.setPhone("15797704512");
        param.setUserPassword("123456");
        userDetailMapper.selectUser();
        UserDetail user2 =userDetailMapper.selectUserByPhoneAndPassword(param);
        userDetailMapper.selectUserByPhoneAndPassword2("15797704512","123456");
        userDetailMapper.updateUserName(1L,"testName");
        UserDetail user =userDetailMapper.selectUserByUserId(1L);
//        testMapper.updateUserName(1L,"test");
        sqlSession.close();
        Assert.assertTrue(true);
    }
}
