package com.breakzhang.rabbit.fanout.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 9:30 2021/4/9
 * @description:
 */
@Component
@RabbitListener(queues = "test.fanout.C.queue")
public class FanoutListenerC {

    private static final Logger LOGGER = LoggerFactory.getLogger(FanoutListenerC.class);


    @RabbitHandler
    public void onMessage(String message) {

        LOGGER.debug("C queue 接收到消息：{}", message);
    }

}
