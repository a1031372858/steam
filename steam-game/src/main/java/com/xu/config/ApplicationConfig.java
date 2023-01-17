package com.xu.config;

import com.xu.bean.UserDetail;
import com.xu.utility.SnowFlakeUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kyo
 * @Date 2021/8/8 - 17:12
 */
@Configuration
public class ApplicationConfig {

    @Bean("kyo")
    public UserDetail MyUesr(){
        UserDetail user = new UserDetail();
        user.setUserId(new SnowFlakeUtility(1,1).nextId());
        user.setDisplayName("许先生");
        return new UserDetail();
    }
}
