package com.xu.config;

import com.xu.quartz.job.SampleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class QuartzSchedulerConfig {


//    @Bean
//    public Scheduler scheduler() throws SchedulerException {
//        return StdSchedulerFactory.getDefaultScheduler();
//    }
//
//    @Bean
//    public SchedulerFactoryBean getSchedulerFactoryBean(){
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setSchedulerName("testSchedulerName");
//        factory.setDataSource(dataSource);
//        factory.setApplicationContextSchedulerContextKey("application");
//        return factory;
//    }

//    @Bean
//    public JobDetail jobDetail(){
//        JobDetail jobDetail = JobBuilder.newJob(SampleJob.class)
//                .withIdentity("job1","group1")
//                .storeDurably()
//                .build();
//        return jobDetail;
//    }
//
//
//    @Bean
//    public JobDetail jobDetail2(){
//        JobDetail jobDetail = JobBuilder.newJob(SampleJob.class)
//                .withIdentity("job2","group1")
//                .storeDurably()
//                .build();
//        return jobDetail;
//    }
//
//    @Bean
//    public Trigger trigger2(){
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 */1 * * * ?");
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .forJob(jobDetail())
//                .withIdentity("trigger2","group1")
//                .withSchedule(scheduleBuilder)
//                .build();
//        return trigger;
//    }
//
//    @Bean
//    public Trigger trigger(){
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 */1 * * * ?");
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .forJob(jobDetail())
//                .withIdentity("trigger1","group1")
//                .withSchedule(scheduleBuilder)
//                .build();
//        return trigger;
//    }

}
