package com.breakzhang.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 13:57 2021/4/8
 * @description: 直连交换机配置
 */
@Configuration
public class DirectRabbitConfig {


    /**
     * 1.声明队列
     */
    @Bean("test.direct.email.queue")
    public Queue TestDirectEmail() {
        // durable:是否持久化,默认是true,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:默认是false, 是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        return new Queue("test.direct.email.queue");
    }

    @Bean("test.direct.log.queue")
    public Queue TestDirectLog() {
        return new Queue("test.direct.log.queue");
    }

    /**
     * 2.声明交换机
     */
    @Bean("fedExchange")
    public Exchange exchange() {
        return new DirectExchange("fedExchange", true, false);
    }

    /**
     * 3.将声明的交换机和队列建立绑定关系,设置路由key
     */
    @Bean
    public Binding bindingExchangeEmail() {
        return BindingBuilder.bind(TestDirectEmail()).to(exchange())
                .with("test.direct.email").noargs();
    }

    @Bean
    public Binding bindingExchangeLog() {
        return BindingBuilder.bind(TestDirectLog()).to(exchange())
                .with("test.direct.log").noargs();
    }

/*
    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }*/


}
