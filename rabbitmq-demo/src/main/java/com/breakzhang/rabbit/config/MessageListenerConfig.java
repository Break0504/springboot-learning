package com.breakzhang.rabbit.config;

import com.breakzhang.rabbit.listener.MessageAckReceiver;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 17:30 2021/4/9
 * @description: 消息监听确认机制配置
 */
@Configuration
public class MessageListenerConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private MessageAckReceiver messageAckReceiver;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        // RabbitMQ默认是自动确认，这里改为手动确认消息
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置一个队列
        container.setQueueNames("test.direct.confirm.queue.1");
        //如果同时设置多个如下： 前提是队列都是必须已经创建存在的
        //  container.setQueueNames("test.direct.confirm.queue.1","test.direct.confirm.queue.2","test.direct.confirm.queue.3");


        //另一种设置队列的方法,如果使用这种情况,那么要设置多个,就使用addQueues
        //container.setQueues(new Queue("test.direct.confirm.queue.1",true));
        //container.addQueues(new Queue("test.direct.confirm.queue.2",true));
        //container.addQueues(new Queue("test.direct.confirm.queue.3",true));
        container.setMessageListener(messageAckReceiver);

        return container;
    }




}
