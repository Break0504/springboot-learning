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

public class HeadersListener3 {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeadersListener3.class);

    @RabbitListener(queues = "test.headers.C.queue")
    public void onMessage(String message) {
        LOGGER.debug("test.headers.C.queue 收到消息：{}", message);
    }
}
