package com.breakzhang;

import com.breakzhang.lock.RedisTemplateLock;
import com.breakzhang.lock.RedissonLock;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
class RedisLockApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLockApplicationTests.class);

    @Autowired
    private RedisTemplateLock redisTemplateLock;
    @Autowired
    private RedissonLock redissonLock;

    //private static final int POOL_SIZE = CurrencyUtil.multiply(Runtime.getRuntime().availableProcessors(), 1.2).intValue() + 1;
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10000));


    @Test
    public void setLock() {
        String result = redisTemplateLock.setlock(20);
        LOGGER.debug("result: {}", result);
    }


    @Test
    public void lock() {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                String result = redisTemplateLock.lock();
                LOGGER.debug("i: {}; result: {}", finalI, result);
            });
        }

        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void redissonLock() {

        for (int i = 0; i < 2000; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                redissonLock.lock(finalI);
            });
        }

        for (int i = 2000; i < 4000; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                redissonLock.lock1(finalI);
            });
        }

        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
