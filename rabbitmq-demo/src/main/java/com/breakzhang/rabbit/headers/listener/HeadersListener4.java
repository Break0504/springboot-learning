package com.breakzhang.rabbit.headers.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 11:33 2021/4/9
 * @description:
 */
@Component

public class HeadersListener4 {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeadersListener4.class);

    @RabbitListener(queues = "test.headers.D.queue")
    public void onMessage(String message) {
        LOGGER.debug("test.headers.D.queue 收到消息：{}", message);
    }
}
