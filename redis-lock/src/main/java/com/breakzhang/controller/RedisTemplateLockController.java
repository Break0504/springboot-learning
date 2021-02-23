package com.breakzhang.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 14:05 2021/2/23
 * @description:
 */
@Controller
public class RedisTemplateLockController {
    private static final Logger logger = LoggerFactory.getLogger(RedisTemplateLockController.class);


    private String lockKey = "redis_lock"; //锁键

    protected long internalLockLeaseTime = 30000;//锁过期时间

    private long timeout = 999999; //获取锁的超时时间

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/setlock")
    @ResponseBody
    public String setlock() {
        redisTemplate.opsForValue().set("stock", 10);
        Object stock = redisTemplate.opsForValue().get("stock");
        return "setStockEnd" + stock;
    }

    @RequestMapping("/lock")
    @ResponseBody
    public String lock() {
        String clientId = UUID.randomUUID().toString();
        logger.debug("clientId: {}", clientId);
        try {
            // 先加锁
            Boolean flag = redisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS);
            logger.debug("flag: {}", flag);
            // 加锁成功 执行扣除库存
            if (flag) {
                // 执行定时器 判断锁是否存在 存在 则延长失效时间
                timer(lockKey, clientId);

                // 读取库存
                Integer stock = (Integer) redisTemplate.opsForValue().get("stock");
                logger.debug("stock: {}", stock);
                if (stock != null && stock > 0) {
                    redisTemplate.opsForValue().set("stock", stock - 1);
                    logger.debug("抢购成功--扣除库存成功-----原库存:{}, 剩余库存: {}", stock, (stock - 1));
                }
            }
        } finally {
            // 释放锁
            String verfiy = (String) redisTemplate.opsForValue().get(lockKey);
            // 判断是否为当前线程加的锁
            if (Objects.equals(verfiy, clientId)) {
                redisTemplate.delete(lockKey);
            }
        }
        return "end";


    }

    /**
     * 5秒钟一次
     * @param lockKey
     * @param clientId
     */
    @Async
    public void timer(String lockKey, String clientId) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、间隔时间单位
        service.scheduleAtFixedRate(()->{
            String result = (String) redisTemplate.opsForValue().get(lockKey);
            if (result != null && result.equals(clientId)) {
                redisTemplate.expire(lockKey, 10 , TimeUnit.SECONDS);
            } else {
                service.shutdown();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }


}
