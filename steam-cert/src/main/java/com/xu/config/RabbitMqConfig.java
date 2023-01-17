package com.xu.config;

import com.xu.common.RabbitMqConstant;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {

    @Bean(RabbitMqConstant.GOLD_DIRECT_EXCHANGE)
    public DirectExchange createGoldDirectExchange(){
        return new DirectExchange(RabbitMqConstant.GOLD_DIRECT_EXCHANGE,true,false);
    }

    @Bean(RabbitMqConstant.GOLD_QUEUE)
    public Queue createGoldQueue(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange",RabbitMqConstant.DEAD_TEST_DIRECT_EXCHANGE);
        args.put("x-dead-letter-routing-key",RabbitMqConstant.DEAD_LETTER_ROUTING_KEY);
        args.put("x-max-length",6);
        return QueueBuilder.durable(RabbitMqConstant.GOLD_QUEUE).withArguments(args).build();
    }

    @Bean
    public Binding BindingGoldQueueToGoldDirectExchange(@Qualifier(RabbitMqConstant.GOLD_DIRECT_EXCHANGE) DirectExchange exchange,
                                                        @Qualifier(RabbitMqConstant.GOLD_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("");
    }


    @Bean(RabbitMqConstant.DEAD_TEST_DIRECT_EXCHANGE)
    public DirectExchange createDeadTestDirectExchange(){
        return new DirectExchange(RabbitMqConstant.DEAD_TEST_DIRECT_EXCHANGE,true,false);
    }

    @Bean(RabbitMqConstant.DEAD_QUEUE)
    public Queue createDeadTestQueue(){
        return QueueBuilder.durable(RabbitMqConstant.DEAD_QUEUE).build();
    }

    @Bean
    public Binding BindingDeadTestQueueToDeadTestDirectExchange(@Qualifier(RabbitMqConstant.DEAD_TEST_DIRECT_EXCHANGE) DirectExchange exchange,
                                                        @Qualifier(RabbitMqConstant.DEAD_QUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(RabbitMqConstant.DEAD_LETTER_ROUTING_KEY);
    }

}
