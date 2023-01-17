package com.xu;

import com.xu.bean.RankCustomWebDto;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.mybatis.spring.annotation.MapperScan;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Queue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@MapperScan("com.xu.mapper")
@ServletComponentScan
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableScheduling
public class GameApplication {


    public static void main(String[] args) throws UnsupportedEncodingException {
        ConfigurableApplicationContext context = SpringApplication.run(GameApplication.class, args);
        RankCustomWebDto dto = context.getBean(RankCustomWebDto.class);
        System.out.println();
//        try {
//            // Grab the Scheduler instance from the Factory
//            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//
//            SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
//                    .startNow()
//                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever())
//                    .build();
//
//            JobDetail jobDetail = JobBuilder.newJob().withIdentity("job1","group1").build();
//            scheduler.scheduleJob(jobDetail,trigger);
//            // and start it off
//            scheduler.start();
//
//            scheduler.shutdown();
//
//        } catch (SchedulerException se) {
//            se.printStackTrace();
//        }
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
