package com.xu.common;

public class RabbitMqConstant {
    /**
     * 金币路由交换机名
     */
    public static final String GOLD_DIRECT_EXCHANGE = "gold.direct.exchange";

    /**
     * 金币队列
     */
    public static final String GOLD_QUEUE = "gold.queue";

    /**
     * 死信测试交换机名
     */
    public static final String DEAD_TEST_DIRECT_EXCHANGE = "dead.test.direct.exchange";

    /**
     * 死信测试队列
     */
    public static final String DEAD_QUEUE = "dead.test.queue";

    /**
     * 死信测试路由Key
     */
    public static final String DEAD_LETTER_ROUTING_KEY = "dead.test.routingKey";

}
