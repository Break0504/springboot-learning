package com.breakzhang.rabbit.direct;

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
 * @description: 直连交换机发送消息
 */
@Component
public class DirectSendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectSendService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${mq.direct.exchange.name}")
    private String exchangName;


    /**
     * 保存用户
     * @param i
     */
    public void saveUser(int i) {
        //订单信息
        String userId = UUID.randomUUID().toString();
        //消息
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String message = "保存用户：" + userId + "  " + createTime;


        LOGGER.debug("exchangName:{}, 正在发送 message:{}", exchangName, message);

        rabbitTemplate.convertAndSend(exchangName, "test.direct.email", message);
        //rabbitTemplate.convertAndSend(exchangName,"test.direct.log", message);
    }
}
