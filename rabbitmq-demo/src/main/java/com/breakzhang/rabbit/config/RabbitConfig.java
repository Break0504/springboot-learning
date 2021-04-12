package com.breakzhang.rabbit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 16:35 2021/4/9
 * @description: Rabbitmq 推送消息确认回调函数
 *
 * ①消息推送到server，但是在server里找不到交换机
 * ②消息推送到server，找到交换机了，但是没找到队列
 * ③消息推送成功
 */
@Configuration
public class RabbitConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(RabbitConfig.class);

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){

        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);

        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            LOGGER.debug("ConfirmCallback 相关数据：{}", correlationData);
            LOGGER.debug("ConfirmCallback 确认情况：{}", ack);
            LOGGER.debug("ConfirmCallback 原因：{}", cause);
        });

        rabbitTemplate.setReturnsCallback((returnedMessage) -> {
            LOGGER.debug("ReturnCallback:   消息：{}", returnedMessage.getMessage());
            LOGGER.debug("ReturnCallback:   回应码：{}", returnedMessage.getReplyCode());
            LOGGER.debug("ReturnCallback:   回应信息：{}", returnedMessage.getReplyText());
            LOGGER.debug("ReturnCallback:   交换机：{}", returnedMessage.getExchange());
            LOGGER.debug("ReturnCallback:   路由键：{}", returnedMessage.getRoutingKey());
        });

        return rabbitTemplate;
    }

    @Bean
    public DirectExchange lonelyDirectExchange() {
        return new DirectExchange("exchangeCallback");
    }

}
