package com.breakzhang.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 16:11 2021/4/8
 * @description: topic 交换机配置
 */
@Configuration
public class TopicRabbitConfig {

    @Bean
    public Queue wxQueue() {
        return new Queue("test.topic.wx.queue");
    }

    @Bean
    public Queue qqQueue() {
        return new Queue("test.topic.qq.queue");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("topic.#");
    }


    /**
     * 将 qqQueue 和 fedExchange 绑定,而且绑定的键值为用上通配路由键规则topic.#
     * 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
     */
    @Bean
    public Binding bindingExchangeMessage() {
        return BindingBuilder.bind(wxQueue()).to(exchange()).with("test.topic.#");
    }

    /**
     * 将 qqQueue 和 fedExchange 绑定,而且绑定的键值为用上通配路由键规则topic.#
     * 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
     */
    @Bean
    public Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(qqQueue()).to(exchange()).with("test.topic.#");
    }


}
