package com.xu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:/application.yml")
@Component
public class ConstantConfig {

    public static String DOMAIN;

    public static final String HOME_RUL = DOMAIN+"/home.html";

    public static final String LOGIN_RUL = DOMAIN+"/login.html";

    @Value("${my-server.domain}")
    public void setDOMAIN(String DOMAIN) {
        ConstantConfig.DOMAIN = DOMAIN;
    }
}
