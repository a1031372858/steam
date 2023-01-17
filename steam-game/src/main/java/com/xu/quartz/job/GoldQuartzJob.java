package com.xu.quartz.job;

import com.xu.utility.RedisUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class GoldQuartzJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(GoldQuartzJob.class);

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        redisUtil.set("gold",10);
        logger.info("GoldQuartzJob开始工作。把金币数量设置为10");
    }
}
