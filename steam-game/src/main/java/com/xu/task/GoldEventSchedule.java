package com.xu.task;

import com.xu.bean.Rank;
import com.xu.controller.GameController;
import com.xu.mapper.RankMapper;
import com.xu.utility.RedisUtil;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class GoldEventSchedule {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RankMapper rankMapper;


    @Scheduled(cron="0 0 0 ? * * ")
//    @Scheduled(cron="0 */1 * * * ?")
//    @Scheduled(cron="* * * * * ? ")
    @SchedulerLock(name = "gold")
    public void clearGivenGold(){
        if(redisUtil.set("gold",10)){
            logger.info("执行定时任务结束，已刷新金币的可领取次数，当前可领取次数为：{}",10);
        }else{
            logger.info("执行定时任务失败");
        }

    }
}
