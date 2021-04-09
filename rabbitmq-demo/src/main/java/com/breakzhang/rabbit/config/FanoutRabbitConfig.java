package com.breakzhang.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 9:05 2021/4/9
 * @description: 扇形交换机配置
 *
 * 创建三个队列，因为扇形交换机不理会绑定的路由键，所以不需要设置路由键
 *
 */
@Configuration
public class FanoutRabbitConfig {


    @Bean
    public Queue fanoutA() {
        return new Queue("test.fanout.A.queue");
    }

    @Bean
    public Queue fanoutB() {
        return new Queue("test.fanout.B.queue");
    }

    @Bean
    public Queue fanoutC() {
        return new Queue("test.fanout.C.queue");
    }


    @Bean
    public FanoutExchange exchangeFanout() {
        return new FanoutExchange("fanout");
    }

    @Bean
    public Binding bindingExchangeA() {
        return BindingBuilder.bind(fanoutA()).to(exchangeFanout());
    }

    @Bean
    public Binding bindingExchangeB() {
        return BindingBuilder.bind(fanoutB()).to(exchangeFanout());
    }

    @Bean
    public Binding bindingExchangeC() {
        return BindingBuilder.bind(fanoutC()).to(exchangeFanout());
    }



}
