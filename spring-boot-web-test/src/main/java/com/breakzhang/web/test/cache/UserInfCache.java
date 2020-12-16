package com.breakzhang.web.test.cache;

import com.breakzhang.web.test.dao.UserInfoDao;
import com.breakzhang.web.test.dto.UserInfDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author: Created by zhangsf
 * @datetime: Created in 15:20 2020/12/14
 * @description:
 */
@Component
public class UserInfCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfCache.class);

    public static final int CACHE_MINUTES = 15; //缓存15分钟

    private static UserInfCache self;

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private RedisTemplate<String, UserInfDto> redisTemplate;

    /**
     * @PostConstruct 只会在项目启动的时候执行一次
     */
    @PostConstruct
    public void init() {
        self = this;
        refreshCache();
    }

    private void refreshCache() {
        LOGGER.debug("刷新所有用户缓存--");
        userInfoDao.listUserInf().forEach(user -> setCache(getKey(user.getUserId()), user));
    }

    private static void refreshUser(String userId) {
        LOGGER.debug("刷新用户：{}，缓存--", userId);
        UserInfDto userInf = self.userInfoDao.getOneByUserId(userId);
        self.setCache(getKey(userId), userInf);
    }

    private void setCache(String key, UserInfDto dto) {
        redisTemplate.opsForValue().set(key, dto, CACHE_MINUTES, TimeUnit.MINUTES);
    }

    public static UserInfDto getUserInf(String userId) {
        LOGGER.debug("查询用户获取缓存 userId：{}", userId);
        UserInfDto userInfDto = self.getCache(getKey(userId));
        if (userInfDto == null) {
            LOGGER.warn("user is null: {}", userId);
            refreshUser(userId);
        }
        return self.getCache(getKey(userId));
    }

    private UserInfDto getCache(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getKey(String userId) {
        return "breakzhang.user.cache-" + userId;
    }


}
