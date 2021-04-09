package com.breakzhang.rabbit.fanout;

import com.breakzhang.rabbit.topic.TopicSendService;
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
 * @datetime: Created in 9:30 2021/4/9
 * @description:
 */
@Component
public class FanoutSendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicSendService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("fanout")
    private String exchangName;


    /**
     * 发送 fanout 消息
     * @param i
     */
    public void sendFanoutMessage(int i) {
        String messageId = UUID.randomUUID().toString();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String message = "第" + i + "次：message：" + messageId + "  " + createTime;

        LOGGER.debug("exchangName:{}, 正在发送 message:{}", exchangName, message);

        rabbitTemplate.convertAndSend(exchangName, null, message);
    }


}
