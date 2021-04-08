package com.breakzhang.rabbit.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 11:18 2021/4/8
 * @description: 主题交换机发送消息
 */
@Component
public class TopicSendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicSendService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("topic.#")
    private String exchangName;


    /**
     * 发送wx topic
     * @param i
     */
    public void sendWxTopic(int i) {
        String userId = UUID.randomUUID().toString();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String message = "wx：" + userId + "  " + createTime;


        LOGGER.debug("exchangName:{}, 正在发送 message:{}", exchangName, message);

        rabbitTemplate.convertAndSend(exchangName, "test.topic.wx", message);
    }

    /**
     * 发送 qq topic
     * @param i
     */
    public void sendQqTopic(int i) {
        String userId = UUID.randomUUID().toString();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String message = "qq：" + userId + "  " + createTime;

        LOGGER.debug("exchangName:{}, 正在发送 message:{}", exchangName, message);

        rabbitTemplate.convertAndSend(exchangName, "test.topic.qq", message);
    }
}
