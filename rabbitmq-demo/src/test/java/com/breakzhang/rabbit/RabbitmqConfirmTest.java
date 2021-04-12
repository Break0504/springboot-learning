package com.breakzhang.rabbit;

import com.breakzhang.rabbit.direct.DirectSendService;
import com.breakzhang.rabbit.filter.ListenerExcludeFilter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 16:45 2021/4/9
 * @description:
 */
@SpringBootTest
@TypeExcludeFilters(ListenerExcludeFilter.class)
public class RabbitmqConfirmTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitmqConfirmTest.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectSendService directSendService;

    /**
     * 测试无交换机的确认机制
     *
     * 结果：channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'test.none.exchange'
     */
    @Test
    void testAck() throws InterruptedException {

        String messageId = UUID.randomUUID().toString();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String message = "消息id：" + messageId + "  " + createTime;

        LOGGER.debug("exchangName:{}, 正在发送 message:{}", "test.none.exchange", message);

        rabbitTemplate.convertAndSend("test.none.exchange", "test.direct.email", message);

        Thread.sleep(10000);
    }

    /**
     * 测试交换机无绑定队列的确认机制
     *
     *     @Bean
     *     public DirectExchange lonelyDirectExchange() {
     *         return new DirectExchange("exchangeCallback");
     *     }
     *
     * 结果  NO_ROUTE
     */
    @Test
    void testAck2() throws InterruptedException {

        String messageId = UUID.randomUUID().toString();
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String message = "消息id：" + messageId + "  " + createTime;

        LOGGER.debug("exchangName:{}, 正在发送 message:{}", "exchangeCallback", message);

        rabbitTemplate.convertAndSend("exchangeCallback", "test.direct.routing", message);

        Thread.sleep(10000);
    }

    /**
     * 测试交换机正常的确认机制
     *
     * 结果 true
     */
    @Test
    void testAck3() throws InterruptedException {

        directSendService.saveUser(1);
        Thread.sleep(1000);

    }


    /**
     * 测试交换机正常的确认机制
     *
     * 结果 true
     */
    @Test
    void testAck4() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            directSendService.sendMessage(i);
            Thread.sleep(20);
        }
        Thread.sleep(10000);
    }



}
