package com.breakzhang.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 10:47 2021/4/9
 * @description: 头交换机
 */
@Configuration
public class HeadersRabbitConfig {

    @Bean
    public Queue headersA() {
        return new Queue("test.headers.A.queue");
    }

    @Bean
    public Queue headersB() {
        return new Queue("test.headers.B.queue");
    }

    @Bean
    public Queue headersC() {
        return new Queue("test.headers.C.queue");
    }

    @Bean
    public Queue headersD() {
        return new Queue("test.headers.D.queue");
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange("amq.headers");
    }

    @Bean
    public Binding bindingHeadersAToExchange() {

        Map<String, Object> header =  new HashMap<>(2);
        header.put("queue", "queue1");
        header.put("bindType", "whereAll");

        return BindingBuilder.bind(headersA()).to(headersExchange()).whereAll(header).match();
    }

    @Bean
    public Binding bindingHeadersBToExchange() {
        Map<String, Object> header =  new HashMap<>(2);
        header.put("queue", "queue2");
        header.put("bindType", "whereAny");
        return BindingBuilder.bind(headersB()).to(headersExchange()).whereAny(header).match();
    }

    @Bean
    public Binding bindingHeadersCToExchange() {
        Map<String, Object> header =  new HashMap<>(2);
        header.put("queue", "queue3");
        header.put("bindType", "whereAll");
        return BindingBuilder.bind(headersC()).to(headersExchange()).whereAll(header).match();
    }

    @Bean
    public Binding bindingHeadersDToExchange() {
        Map<String, Object> header =  new HashMap<>(2);
        header.put("queue", "queue4");
        header.put("bindType", "whereAny");
        return BindingBuilder.bind(headersD()).to(headersExchange()).whereAny(header).match();
    }

}
