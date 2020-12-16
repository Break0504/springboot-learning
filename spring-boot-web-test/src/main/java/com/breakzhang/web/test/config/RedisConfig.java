package com.breakzhang.web.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * @author: Created by zhangsf
 * @datetime: Created in 15:14 2020/12/14
 * @description:
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class) ;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        LOGGER.info("RedisConfig == >> redisTemplate ");

        //创建RedisTemplate对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //序列化对象
        //简单的字符串序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //jdk序列化
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        //Json序列化
        /*GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer();*/
        //设置String键的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //设置String值的序列化方式
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        return redisTemplate;
    }



}
