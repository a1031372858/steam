package com.xu.controller;

import com.xu.common.Constant;
import com.xu.utility.LuaScript;
import com.xu.utility.RedisUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("appointment")
@RestController
public class AppointmentController {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LuaScript luaScript;

    @ApiOperation("清空预约人数")
    @GetMapping("clearAmount")
    public String clearAppointmentAmount() {
        redisUtil.set("hospitalId:" + Constant.HOSPITAL_ID+":hour:9", 10);
        redisUtil.set("hospitalId:" + Constant.HOSPITAL_ID+":hour:10", 10);
        redisUtil.set("hospitalId:" + Constant.HOSPITAL_ID+":hour:11", 10);
        redisUtil.set("hospitalId:" + Constant.HOSPITAL_ID+":hour:14", 10);
        redisUtil.set("hospitalId:" + Constant.HOSPITAL_ID+":hour:15", 10);
        redisUtil.set("hospitalId:" + Constant.HOSPITAL_ID+":hour:16", 10);
        redisUtil.del("hospitalId:" + Constant.HOSPITAL_ID+":appointmentUserSet");
        logger.info("执行定时任务结束，已刷新金币的可领取次数，当前可领取次数为：{}", 10);
        return "已刷新金币的可领取次数";
    }

    @PostMapping("setAmount")
    public String setAppointmentAmount(int hour, int amount) {

        if (hour < 9 || (hour >= 12 && hour < 14) || hour >= 17) {
            return "该时段不可预约";
        }
        if (amount < 0 || amount >= 100) {
            return "预约数量超出限制";
        }
        redisUtil.set("hospitalId:" + Constant.HOSPITAL_ID + ":hour:" + hour, amount);
        return "设置成功";
    }


    @PostMapping("appointmentUser")
    public String appointmentHour(int hour,long userId){
        int currentHour = LocalDateTime.now().getHour();
        if(currentHour<6)return "6点之后才开放预约";
        if(currentHour>=hour)return "该时段预约已结束";

        if (hour < 9 || (hour >= 12 && hour < 14) || hour >= 17) {
            return "该时段不可预约";
        }

        String appointmentKey = "hospitalId:" + Constant.HOSPITAL_ID+":hour:"+hour;
        String userIdSetKey = "hospitalId:" + Constant.HOSPITAL_ID+":appointmentUserSet";
        long result = luaScript.allowAppointment(appointmentKey,userIdSetKey,String.valueOf(userId));
        if(result>0){
            return "预约成功";
        }
        return "预约失败";
    }

    @PostMapping("appointmentCancel")
    public String appointmentCancel(int hour,long userId){
        String appointmentKey = "hospitalId:" + Constant.HOSPITAL_ID+":hour:"+hour;
        String userIdSetKey = "hospitalId:" + Constant.HOSPITAL_ID+":appointmentUserSet";
        long result = luaScript.allowAppointment(appointmentKey,userIdSetKey,String.valueOf(userId));
        if(result>0){
            return "预约成功";
        }
        return "预约失败";
    }
}
