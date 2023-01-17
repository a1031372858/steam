package com.xu.listener;

import com.rabbitmq.client.Channel;
import com.xu.common.RabbitMqConstant;
import com.xu.controller.GameController;
import com.xu.mapper.AccountMapper;
import com.xu.utility.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;

//@Component
public class GoldQueueListener {
    private static final Logger logger = LoggerFactory.getLogger(GoldQueueListener.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AccountMapper accountMapper;

    @RabbitListener(queues = {RabbitMqConstant.GOLD_QUEUE})
    @Transactional
    public void giveGold(Channel channel, Message message) throws IOException {
//        try {
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//            return;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            String msg = new String(message.getBody());
            long userId = Long.parseLong(msg);
            accountMapper.updateAccountGold(userId,1);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            logger.info("[GoldQueueListener] userId:"+msg);
        }catch (Exception e){
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            logger.error("GoldQueueListener出现异常，应该重试");
            logger.error(e.toString());
        }
    }
}
