package com.breakzhang.rabbit;

import com.breakzhang.rabbit.direct.DirectSendService;
import com.breakzhang.rabbit.fanout.FanoutSendService;
import com.breakzhang.rabbit.filter.ListenerExcludeFilter;
import com.breakzhang.rabbit.topic.TopicSendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TypeExcludeFilters(ListenerExcludeFilter.class)
class RabbitmqDemoApplicationTests {

    @Autowired
    private DirectSendService directSendService;
    @Autowired
    private TopicSendService topicSendService;
    @Autowired
    private FanoutSendService fanoutSendService;

    @Test
    void contextLoadsDirect() throws InterruptedException {

        for (int i = 0; i < 20; i++) {
            directSendService.saveUser(i);

            Thread.sleep(2);
        }

        Thread.sleep(500000);
    }


    @Test
    void contextLoadsTopic() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            topicSendService.sendWxTopic(i);
            Thread.sleep(2);
            topicSendService.sendQqTopic(i);
            Thread.sleep(2);
        }


        Thread.sleep(500000);
    }

    @Test
    void contextLoadsFanout() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            fanoutSendService.sendFanoutMessage(i);
            Thread.sleep(2);
        }
        Thread.sleep(500000);
    }



}
