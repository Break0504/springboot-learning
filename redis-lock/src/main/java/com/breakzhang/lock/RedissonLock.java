package com.breakzhang.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 15:06 2021/2/23
 * @description:
 */
@Service
public class RedissonLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonLock.class);

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RedisTemplate redisTemplate;

    private String lockKey = "redis_lock";

    public void lock(int i) {
        if (redissonClient == null) {
            LOGGER.warn("redissonClient is null");
            return;
        }

        RLock lock = redissonClient.getLock(lockKey);

        int timeoutSeconds = 1, expireSeconds = 10;
        try {
            boolean res = lock.tryLock(timeoutSeconds, expireSeconds, TimeUnit.SECONDS);

            if (!res) {
                LOGGER.debug("抢购失败: {}，获取锁失败：{}", i, res);
                return;
            }
            // 读取库存
            Integer stock = (Integer) redisTemplate.opsForValue().get("stock");
            if (stock != null && stock > 0) {
                redisTemplate.opsForValue().set("stock", stock - 1);
                LOGGER.debug("抢购成功: {}--扣除库存成功-----原库存:{}, 剩余库存: {}", i, stock, (stock - 1));
            } else {
                LOGGER.debug("抢购失败: {}--库存:{}", i, stock);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                lock.unlock();
            } catch (Exception e) {
                LOGGER.debug("解锁异常：{}", e);
            }

        }


    }


    public void lock1(int i) {
        if (redissonClient == null) {
            LOGGER.warn("redissonClient is null");
            return;
        }

        RLock lock = redissonClient.getLock(lockKey);

        int timeoutSeconds = 1, expireSeconds = 10;
        try {
            boolean res = lock.tryLock(timeoutSeconds, expireSeconds, TimeUnit.SECONDS);

            if (!res) {
                LOGGER.debug("抢购失败: {}，获取锁失败：{}", i, res);
                return;
            }
            // 读取库存
            Integer stock = (Integer) redisTemplate.opsForValue().get("stock");
            if (stock != null && stock > 0) {
                redisTemplate.opsForValue().set("stock", stock - 1);
                LOGGER.debug("抢购成功: {}--扣除库存成功-----原库存:{}, 剩余库存: {}", i, stock, (stock - 1));
            } else {
                LOGGER.debug("抢购失败: {}--库存:{}", i, stock);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                lock.unlock();
            } catch (Exception e) {
                LOGGER.debug("解锁异常：{}", e);
            }

        }


    }



}
