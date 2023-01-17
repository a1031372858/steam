package com.xu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TestProperties.class)
public class TestServiceAutoConfiguration {

    @Autowired
    TestProperties testProperties;

    @Bean
    public TestService testService(){
        TestService testService = new TestService();
        testService.setTestProperties(testProperties);
        return testService;
    }
}
