package com.breakzhang.rabbit.listener;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 17:32 2021/4/9
 * @description: 消息监听确认机制
 */
@Component
public class MessageAckReceiver implements ChannelAwareMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAckReceiver.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {

            LOGGER.debug("队列名：{}，消息监听到的消息：{}", message.getMessageProperties().getConsumerQueue(), message.toString());

            //第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
            channel.basicAck(deliveryTag, true);


        } catch (Exception e) {
            //第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
            channel.basicReject(deliveryTag, false);
            LOGGER.error("消费异常：{}", e);
        }



    }
}
