package com.xu.quartz.job;

import com.xu.utility.RedisUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

//@PersistJobDataAfterExecution
//@DisallowConcurrentExecution
@Component
public class SampleJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(SampleJob.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private Scheduler scheduler;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        try {
            String name =context.getScheduler().getSchedulerInstanceId();
            if(scheduler==context.getScheduler()){
                logger.info("两者相同");
            }
            logger.info("SchedulerInstanceId"+name);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        redisUtil.set("gold",10);
        logger.info("SampleJob开始工作。把金币数量设置为10");
    }
}
