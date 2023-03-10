package com.xu.controller;

import com.alibaba.druid.util.StringUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sun.istack.internal.NotNull;
import com.xu.TestService;
import com.xu.bean.custom.ResponseWebDto;
import com.xu.common.Constant;
import com.xu.common.RabbitMqConstant;
import com.xu.utility.LuaScript;
import com.xu.utility.RedisUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Validated
@RestController
@RequestMapping("test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LuaScript luaScript;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TestService testService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @GetMapping("test1")
    public String test1() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000, 1500, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));
        for (int i = 0; i < 1500; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> getGold(String.valueOf(finalI)));
        }
        return "??????????????????";
    }

    @ApiOperation("??????")
    @GetMapping("test2")
    @HystrixCommand(fallbackMethod = "test2Callback")
    public String test2() {
        testService.sayHello();
        Map<String, String> param = new HashMap<>();
        return testService.sayHello();
//        return restTemplate.getForObject("http://steam-cert/cert/test2", String.class);
    }

    public String test2Callback() {
        return "????????????";
    }


    @GetMapping("checkTicket")
    public String checkTicket() {
        Map<String, String> param = new HashMap<>();
        param.put("ticketCode", "c2ba3909-5c85-4cd2-86ff-f6377366edc7");
        param.put("hostIp", "1");
        ResponseEntity<ResponseWebDto> responseWebDto = restTemplate.getForEntity("http://steam-cert/cert/ticket/checkTicket?ticketCode={ticketCode}&hostIp={hostIp}", ResponseWebDto.class, param);
        ResponseWebDto result = responseWebDto.getBody();
        return result.getMessage();
    }

    @ApiOperation("??????????????????")
    @GetMapping("goldQueue/clear")
    public String clearGoldQueue() {
        redisUtil.set("gold", 10);
        Object goldNum = redisUtil.get("gold");
        logger.info("??????????????????????????????????????????????????????????????????????????????????????????{}", goldNum);
        return "?????????????????????????????????";
    }


    @PostMapping("gold/set")
    public String setGoldAmount(int amount) {
        redisUtil.set("gold", amount);
        return "??????????????????";
    }



    @GetMapping("gold/give/{userId}")
    public String getGold(@PathVariable @NotNull String userId) {
        if (StringUtils.isEmpty(userId)) {
            return "??????id??????";
        }
        long result = luaScript.allowGetGold("gold", "gold:userIdSet", userId);

        if (result > 0) {
            rabbitTemplate.convertAndSend(RabbitMqConstant.GOLD_DIRECT_EXCHANGE, "", userId, new CorrelationData("gold"));
            logger.info("userId=" + userId + "?????????????????????????????????");
            return "???????????????";
        } else {
            logger.info("userId=" + userId + "???????????????????????????????????????????????????");
            return "??????????????????";
        }
    }


    public String setDoctorAmount(LocalDateTime dateTime, int amount) {
        if (dateTime.getHour() > 17 || dateTime.getHour() <= 9)
            if (dateTime != null && amount > 0 && amount < 100) {
                redisUtil.set("1", amount);
            }
        return "????????????";
    }


}
