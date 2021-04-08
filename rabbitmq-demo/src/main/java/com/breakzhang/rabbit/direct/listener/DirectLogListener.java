package com.breakzhang.rabbit.direct.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 12:46 2021/4/8
 * @description:
 */
@Component
@RabbitListener(queues = "test.direct.log.queue")
public class DirectLogListener /*implements MessageListener*/ {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectLogListener.class);

    //@Override
    @RabbitHandler
    public void onMessage(String message) {


        LOGGER.debug("监听到的 test.direct.log 消息：{}", message);
    }

}
