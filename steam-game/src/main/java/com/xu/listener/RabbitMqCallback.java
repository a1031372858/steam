package com.xu.listener;

import com.xu.utility.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class RabbitMqCallback implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqCallback.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisUtil redisUtil;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            logger.info("交换机成功接收到消息,消息ID："+correlationData.getId());
        }else{
            logger.info("交换机接收到消息失败,消息ID："+correlationData.getId());
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        logger.info("消息{}被交换机{}退回，退回原因：{}，路由Key:{}",returned.getMessage(),returned.getExchange(),returned.getReplyCode(),returned.getRoutingKey());
    }
}
