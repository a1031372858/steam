package com.xu.config;

import com.xu.quartz.job.SampleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


//@Component
public class StartQuartzListeer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        TriggerKey triggerKey = new TriggerKey("trigger1", "group1");
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if(trigger==null){
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity("trigger1", "group1")
                        .startNow()
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(40)
                                .repeatForever())
                        .build();

            }
            JobDetail jobDetail = JobBuilder.newJob(SampleJob.class)
                    .withIdentity("job1", "group1")
                    .build();
            scheduler.scheduleJob(jobDetail,trigger);

            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
