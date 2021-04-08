package com.breakzhang.rabbit.topic.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 12:46 2021/4/8
 * @description: 监听队列，默认是直连模式
 */
@Component
@RabbitListener(queues = "test.topic.wx.queue")
public class TopicWxListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicWxListener.class);

    @RabbitHandler
    public void onMessage(String message) {
        LOGGER.debug("wx 监听到的 test.topic.wx 消息：{}", message);
    }

}
