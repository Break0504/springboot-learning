package com.breakzhang.rabbit.topic.listener;

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
@RabbitListener(queues = "test.topic.qq.queue")
public class TopicQqListener /*implements MessageListener*/ {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicQqListener.class);

    //@Override
    @RabbitHandler
    public void onMessage(String message) {


        LOGGER.debug("qq监听到的 test.topic.qq 消息：{}", message);
    }

}
