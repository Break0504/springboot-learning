package com.breakzhang.rabbit.headers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
 * @description: 头交换机发送消息
 */
@Component
public class HeaderSendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderSendService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("amq.headers")
    private String exchangName;


    /**
     * 发送 Headers 消息
     * @param i
     */
    public void sendHeadersQueue1(int i) {

        MessageProperties properties = new MessageProperties();
        properties.setHeader("queue", "queue1");
        properties.setHeader("bindType", "whereAll");
        convertAndSend(i, properties);
    }

    public void sendHeadersQueue2(int i) {
        MessageProperties properties = new MessageProperties();
        properties.setHeader("queue", "queue2");
        properties.setHeader("bindType", "whereAny");
        convertAndSend(i, properties);
    }

    public void sendHeadersQueue3(int i) {
        MessageProperties properties = new MessageProperties();
        properties.setHeader("queue", "queue3");
        properties.setHeader("bindType", "whereAll");
        convertAndSend(i, properties);
    }

    public void sendHeadersQueue4(int i) {
        MessageProperties properties = new MessageProperties();
        properties.setHeader("queue", "queue4");
        properties.setHeader("bindType", "whereAny");
        convertAndSend(i, properties);
    }

    private void convertAndSend(int i, MessageProperties properties) {
        String messageId = UUID.randomUUID().toString();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String message = "第" + i + " 消息 message：" + messageId + "  " + createTime;
        Message msg = new Message(message.getBytes(), properties);
        LOGGER.debug("exchangName:{}, 正在发送 message:{}", exchangName, msg);
        rabbitTemplate.convertAndSend(exchangName, null, msg);
    }


}
